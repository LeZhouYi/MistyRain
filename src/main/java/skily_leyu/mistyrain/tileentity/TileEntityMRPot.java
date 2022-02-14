package skily_leyu.mistyrain.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.config.MRProperty;
import skily_leyu.mistyrain.config.MRSettings;
import skily_leyu.mistyrain.utility.MRItemUtils;
import skily_leyu.mistyrain.utility.MRMathUtils;
import skily_leyu.mistyrain.utility.type.MRPlant;
import skily_leyu.mistyrain.utility.type.MRPot;
import skily_leyu.mistyrain.utility.type.MRPlant.PlantStage;

public class TileEntityMRPot extends TileEntity{

	private final String TAG_SOIL_INV = "soilInventTag";
	private final String TAG_PLANT_INV = "plantInventTag";
	private final String TAG_WATER_TANK = "waterTankTag";
	private final String TAG_POT_KEY = "potKeyTag";
	private final String TAG_PLANT_STAGE = "plantStageTage";

	protected String key; //判别花盆类型及属性

    protected ItemStackHandler soilInventory;
	protected ItemStackHandler plantInventory;
	protected FluidTank waterTank;

	private MRPot mrPot; //缓存

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
		this.mrPot = MRSettings.potMap.getMRPot(key);
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

    /**
	 * 添加泥土并返回放置数量，不处理物品数量的减少，不校验物品是否合法
	 *
	 * @param itemStack
	 * @return
	 */
	public int addItem(ItemStack itemStack) {
		//添加泥土
		if(this.mrPot.isSuitSoil(itemStack)){
			return MRItemUtils.addItemInHandlerOneTime(this.soilInventory, itemStack);
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

		if(compound.hasKey(TAG_POT_KEY)){
			this.key = compound.getString(TAG_POT_KEY);
			init(key);
		}
		if(compound.hasKey(TAG_SOIL_INV)) {
			this.soilInventory.deserializeNBT(compound.getCompoundTag(TAG_SOIL_INV));
		}
		if(compound.hasKey(TAG_PLANT_INV)){
			this.plantInventory.deserializeNBT(compound.getCompoundTag(TAG_PLANT_INV));
		}
		if(compound.hasKey(TAG_WATER_TANK)){
			this.waterTank.writeToNBT(compound.getCompoundTag(TAG_WATER_TANK));
		}
		if(compound.hasKey(TAG_PLANT_STAGE)){
			this.plant = new ArrayList<>();
			this.plantStage = new ArrayList<>();
			int[] stageArray = compound.getIntArray(TAG_PLANT_STAGE);
			for(int i = 0;i<stageArray.length;i++){
				this.plant.add(MRSettings.animalPlantMap.getPlant(this.plantInventory.getStackInSlot(i)));
				this.plantStage.add(PlantStage.values()[stageArray[i]]);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString(TAG_POT_KEY, this.key);
		writeUpdatePacket(compound);
		return compound;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 1, writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	public void writeUpdatePacket(NBTTagCompound nbt){
		nbt.setTag(TAG_SOIL_INV, this.soilInventory.serializeNBT());
		nbt.setTag(TAG_PLANT_INV, this.plantInventory.serializeNBT());
		nbt.setTag(TAG_WATER_TANK, this.waterTank.writeToNBT(new NBTTagCompound()));
		if(this.plantStage!=null&&this.plantStage.size()>0){
			int plantSize = this.plantStage.size();
			int[] stageArray = new int[plantSize];
			for(int i=0;i<this.plant.size();i++){
				stageArray[i] = this.plantStage.get(i).ordinal();
			}
			nbt.setIntArray(TAG_PLANT_STAGE, stageArray);
		}
	}

}
