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
import skily_leyu.mistyrain.network.MRMessages;
import skily_leyu.mistyrain.network.MessageMRPot;
import skily_leyu.mistyrain.utility.MRItemUtils;
import skily_leyu.mistyrain.utility.MRMathUtils;
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

	public TileEntityMRPot() {}
	
    public TileEntityMRPot(String key){
		init(key);
    }

	/**
	 * 初始化数据
	 * @param key
	 */
	protected void init(String key){
		this.key = key;
		if(this.mrPot==null){
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
		}
	}

    /**
	 * 添加泥土并返回放置数量，不处理物品数量的减少，不校验物品是否合法
	 *
	 * @param itemStack
	 * @return
	 */
	public int addItem(ItemStack itemStack) {
		//添加泥土
		if(this.mrPot.isSuitSoil(itemStack)){
			int amount = MRItemUtils.addItemInHandlerOneTime(this.soilInventory, itemStack);
			if(amount>0){
				NBTTagCompound compound = new NBTTagCompound();
				compound.setTag("SoilInventory", this.soilInventory.serializeNBT());
				System.out.print("test");
				MRMessages.INSTANCE.sendToServer(new MessageMRPot().setMessage(this, compound));
				return amount;
			}
		}
		//添加植物
		else if(MRSettings.animalPlantMap.isPlant(itemStack)){

			int size = MRMathUtils.minInteger(this.soilInventory.getSlots(), this.plantInventory.getSlots());
			MRPlant mrPlant = MRSettings.animalPlantMap.getPlant(itemStack);

			for(int i = 0;i<size;i++){
				ItemStack soilStack = this.soilInventory.getStackInSlot(i);
				ItemStack plantStack = this.plantInventory.getStackInSlot(i);

				if(plantStack.isEmpty()){
					if(!soilStack.isEmpty()&&MRSettings.soilMap.isSuitSoil(mrPlant.getSoilType(), soilStack)){
						//添加植物成功
						this.plant.add(mrPlant);
						this.plantStage.add(PlantStage.SEED_DROP);

						return MRItemUtils.addItemInHandlerOneTime(this.plantInventory, itemStack);
					}
				}
			}
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

		if(compound.hasKey("PotKey")){
			this.key = compound.getString("PotKey");
			init(key);
		}
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
			this.plant = new ArrayList<>();
			this.plantStage = new ArrayList<>();
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
		if(this.plantStage!=null&&this.plantStage.size()>0){
			int plantSize = this.plantStage.size();
			int[] stageArray = new int[plantSize];
			for(int i=0;i<this.plant.size();i++){
				stageArray[i] = this.plantStage.get(i).ordinal();
			}
			compound.setIntArray("PlantStage", stageArray);
		}
		return compound;
	}

}
