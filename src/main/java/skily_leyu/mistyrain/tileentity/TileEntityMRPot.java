package skily_leyu.mistyrain.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.config.MRProperty;
import skily_leyu.mistyrain.config.MRSettings;
import skily_leyu.mistyrain.utility.MRItemUtils;
import skily_leyu.mistyrain.utility.type.MRPlant;
import skily_leyu.mistyrain.utility.type.MRPot;
import skily_leyu.mistyrain.utility.type.MRPlant.PlantStage;

public class TileEntityMRPot extends TileEntity{

	protected String key;
    protected ItemStackHandler soilInventory;
	protected ItemStackHandler plantInventory;
	protected FluidTank waterTank;

	private MRPot mrPot;

	private List<MRPlant> plant;
	private List<PlantStage> plantStage;

    public TileEntityMRPot(String key){
		init(key);
    }

	/**
	 * 初始化数据
	 * @param key
	 */
	protected void init(String key){
		this.mrPot = MRSettings.potMap.getMRPot(key);
		if(this.mrPot == null){
			this.mrPot = MRSettings.potMap.getDeafultMRPot();
		}
        this.soilInventory = new ItemStackHandler(mrPot.getSoidSize()){
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
		this.plantInventory = new ItemStackHandler(mrPot.getPlantSize()){
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}
		};
		this.waterTank = new FluidTank(this.mrPot.getTankSize());
		this.plant = new ArrayList<>();
		this.plantStage = new ArrayList<>();
	}

    /**
	 * 添加泥土并返回放置数量，不处理物品数量的减少，不校验物品是否合法
	 *
	 * @param itemStack
	 * @return
	 */
	public int addSoil(ItemStack itemStack) {
		if(this.mrPot.isSuitSoil(itemStack)){
			return MRItemUtils.addItemInItemStackHandler(this.soilInventory, itemStack);
		}
		return 0;
	}

    /**
	 * 获取存储的土壤
	 * @return
	 */
	public ItemStack getSoil() {
		for (int index = 0; index < this.soilInventory.getSlots(); index++) {
			ItemStack slotItemStack = this.soilInventory.getStackInSlot(index);
			if (!slotItemStack.isEmpty()) {
				return slotItemStack;
			}
		}
		return ItemStack.EMPTY;
	}

    /**
     * 获取方向
     * @return
     */
    public EnumFacing getFacing(){
        return world.getBlockState(this.pos).getValue(MRProperty.FACING_HORIZONTAL);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.key = compound.hasKey("PotKey")?compound.getString("PotKey"):MRProperty.WOODEN_BASE;
		init(key);
		if(compound.hasKey("SoilInventory")) {
			this.soilInventory.deserializeNBT(compound.getCompoundTag("SoilInventory"));
		}
		if(compound.hasKey("PlantInventory")){
			this.plantInventory.deserializeNBT(compound.getCompoundTag("PlantInventory"));
		}
		if(compound.hasKey("WaterTank")){
			this.waterTank.writeToNBT(compound.getCompoundTag("WaterTank"));
		}
		if(compound.hasKey("PlantStage")){
			int[] stageArray = compound.getIntArray("PlantStage");
			for(int i = 0;i<stageArray.length;i++){
				this.plant.add(MRSettings.animalPlantMap.getPlant(this.plantInventory.getStackInSlot(i)));
				this.plantStage.add(PlantStage.values()[stageArray[i]]);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("PotKey", this.key);
		compound.setTag("SoilInventory", this.soilInventory.serializeNBT());
		compound.setTag("PlantInventory", this.plantInventory.serializeNBT());
		compound.setTag("WaterTank", this.waterTank.writeToNBT(new NBTTagCompound()));
		int plantSize = this.plantStage.size();
		if(plantSize>0){
			int[] stageArray = new int[plantSize];
			for(int i=0;i<this.plant.size();i++){
				stageArray[i] = this.plantStage.get(i).ordinal();
			}
			compound.setIntArray("PlantStage", stageArray);
		}
		return compound;
	}

}
