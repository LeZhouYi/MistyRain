package skily_leyu.mistyrain.tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public class TileEntityMRPot extends TileEntityMR{

    private final String TAG_SOIL_INV = "soilInventTag";
    private final String TAG_POT_KEY = "potKeyTag";
	private final String TAG_PLANT_INV = "plantInventTag";
	private final String TAG_WATER_TANK = "waterTankTag";
	private final String TAG_PLANT_STAGE = "plantStageTage";

    protected String key;
    protected ItemStackHandler soilInventory;
	protected ItemStackHandler plantInventory;
	protected FluidTank waterTank;

	private List<PlantStage> plantStage;

    private MRPot mrPot; //缓存
	private List<MRPlant> plant;

    public TileEntityMRPot(){}

    @Override
    public NBTTagCompound writeUpdatePacket(NBTTagCompound nbt) {
        return null;
    }

    /**
	 * 初始化数据
	 * @param key
	 */
	public TileEntityMRPot init(String key){
		this.key = key;
		this.mrPot = MRSettings.potMap.getMRPot(key);
		this.soilInventory = new ItemStackHandler(mrPot.getSoilSize()){
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
        return this;
    }

    @Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.key = compound.getString(TAG_POT_KEY);
		init(key);
		this.soilInventory.deserializeNBT(compound.getCompoundTag(TAG_SOIL_INV));
		this.plantInventory.deserializeNBT(compound.getCompoundTag(TAG_PLANT_INV));
		this.waterTank.writeToNBT(compound.getCompoundTag(TAG_WATER_TANK));
		if(compound.hasKey(TAG_PLANT_STAGE)){
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
        compound.setTag(TAG_SOIL_INV, this.soilInventory.serializeNBT());
		compound.setTag(TAG_PLANT_INV, this.plantInventory.serializeNBT());
		compound.setTag(TAG_WATER_TANK, this.waterTank.writeToNBT(new NBTTagCompound()));
		if(this.plantStage!=null&&this.plantStage.size()>0){
			int plantSize = this.plantStage.size();
			int[] stageArray = new int[plantSize];
			for(int i=0;i<this.plant.size();i++){
				stageArray[i] = this.plantStage.get(i).ordinal();
			}
			compound.setIntArray(TAG_PLANT_STAGE, stageArray);
		}
		return compound;
	}

	/**
	 * 获取存储的土壤
	 * @return
	 */
	public ItemStack getSoil(int index) {
		if(this.soilInventory.getSlots()>index){
			return this.soilInventory.getStackInSlot(0);
		}
		return ItemStack.EMPTY;
	}

	/**
	 * 添加泥土并返回放置数量，不处理物品数量的减少，不校验物品是否合法
	 *
	 * @param itemStack
	 * @return
	 */
	public int addItem(ItemStack itemStack) {
		// 添加泥土
		if(this.mrPot.isSuitSoil(itemStack)){
			return MRItemUtils.addItemInHandler(this.soilInventory, itemStack);
		}
		// 添加植物
		else if(MRSettings.animalPlantMap.isPlant(itemStack)){

			int size = Math.min(this.soilInventory.getSlots(), this.plantInventory.getSlots());
			MRPlant mrPlant = MRSettings.animalPlantMap.getPlant(itemStack);

			for(int i = 0;i<size;i++){
				ItemStack soilStack = this.soilInventory.getStackInSlot(i);
				ItemStack plantStack = this.plantInventory.getStackInSlot(i);

				if(plantStack.isEmpty()){
					if(!soilStack.isEmpty()&&MRSettings.soilMap.isSuitSoil(mrPlant.getSoilType(), soilStack)){
						//添加植物成功
						this.plant.add(mrPlant);
						this.plantStage.add(PlantStage.SEED_DROP);

						return MRItemUtils.addItemInHandler(this.plantInventory, itemStack);
					}
				}
			}
		}
		return 0;
	}

	/**
	 * 获取植物当前状态的模型路径
	 * @param index
	 * @return
	 */
	@Nullable
	public ModelResourceLocation getPlantModel(int index){
		if(this.plantInventory.getSlots()>index){
			ItemStack plantStack = this.plantInventory.getStackInSlot(index);
			if(!plantStack.isEmpty()){
				String registyName = plantStack.getItem().getRegistryName().toString();
				String variant = String.format("%s=%d",MRProperty.PLANT_STAGE_TAG,this.plantStage.get(index).ordinal());
				return new ModelResourceLocation(registyName,variant);
			}
		}
		return null;
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

}