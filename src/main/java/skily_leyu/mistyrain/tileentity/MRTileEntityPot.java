package skily_leyu.mistyrain.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.feature.property.MRProperty;
import skily_leyu.mistyrain.feature.property.PotProperties;
import skily_leyu.mistyrain.feature.property.PotProperty;
import skily_leyu.mistyrain.feature.utility.MRItemStackUtils;

public class MRTileEntityPot extends MRTileEntity {

	protected ItemStackHandler soilInventory;
	protected PotProperty potProperty;
	protected FluidTank[] fluidTanks;

	public MRTileEntityPot init(PotProperty potProperty) {
		this.potProperty = potProperty;
		this.soilInventory = new ItemStackHandler(potProperty.getSlotSize()) {
			@Override
			public int getSlotLimit(int slot) {
				return potProperty.getStackSize();
			}
		};
		this.fluidTanks = new FluidTank[this.potProperty.getTankSize()];
		for(int i = 0;i<this.fluidTanks.length;i++){
			this.fluidTanks[i] = new FluidTank(potProperty.getVolumeSize());
		}
		return this;
	}

	/**
	 * 返回存在的泥土，若不存在则返回EMPTY
	 * @return
	 */
	public ItemStack getSoilItemStack() {
		for (int index = 0; index < this.soilInventory.getSlots(); index++) {
			ItemStack slotItemStack = this.soilInventory.getStackInSlot(index);
			if (!slotItemStack.isEmpty()) {
				return slotItemStack;
			}
		}
		return ItemStack.EMPTY;
	}

	/**
	 * 返回方块朝向
	 *
	 * @return
	 */
	public EnumFacing getFacing() {
		return world.getBlockState(getPos()).getValue(MRProperty.FACING);
	}

	/**
	 * 判断物品是否属于可放置的泥土
	 *
	 * @param itemStack
	 * @return
	 */
	public boolean isSoil(ItemStack itemStack) {
		return this.potProperty.containsSoil(itemStack);
	}

	/**
	 * 添加泥土并返回放置数量，不处理物品数量的减少，不校验物品是否合法
	 *
	 * @param itemStack
	 * @return
	 */
	public int addSoil(ItemStack itemStack) {
		return MRItemStackUtils.addItemInItemStackHandler(this.soilInventory, itemStack);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		String potNameString = (compound.hasKey("Potproperty")) ? compound.getString("Potproperty") : null;
        init(PotProperties.getPotProperty(potNameString));
		if(compound.hasKey("SoilInventory")) {
			this.soilInventory.deserializeNBT(compound.getCompoundTag("SoilInventory"));
		}
		if(compound.hasKey("FluidTanks")){
			NBTTagCompound fluidCompound = (NBTTagCompound) compound.getTag("FluidTanks");
			for(int index = 0; index< this.potProperty.getTankSize();index++){
				if(fluidCompound.hasKey("FluidTank"+index)){
					this.fluidTanks[index].readFromNBT(fluidCompound.getCompoundTag("FluidTank"+index));
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("SoilInventory", this.soilInventory.serializeNBT());
		compound.setString("PotProperty", this.potProperty.getName());

		NBTTagCompound fluidCompound = new NBTTagCompound();
		for(int index = 0;index<this.fluidTanks.length;index++){
			fluidCompound.setTag("FluidTank"+index, this.fluidTanks[index].writeToNBT(new NBTTagCompound()));
		}
		compound.setTag("FluidTanks", fluidCompound);

		return compound;
	}

}