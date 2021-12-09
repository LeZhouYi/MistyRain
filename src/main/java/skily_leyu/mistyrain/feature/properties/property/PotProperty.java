package skily_leyu.mistyrain.feature.properties.property;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class PotProperty {

    private String name; //用于标记和获取PotProty,大驼峰式
    private int slotSize; //设置花盆储存泥土的格数
    private int stackSize; //设置花盆单格储存泥土的数量
    private int tankSize; //水槽数量
    private int volumeSize; //水槽储量
    private List<Item> blockList; //可以放置的方块（土壤方块）
    private List<Fluid> fluidList; //可以放置的水槽

    /**
     * 该物品是否在在土壤白名单
     * @param itemStack
     * @return
     */
    public boolean containsSoil(ItemStack itemStack){
        return this.blockList.contains(itemStack.getItem());
    }

    /**
     * 该物品的流体是否在白名单中
     * @param itemStack
     * @return
     */
    public boolean containsFluid(ItemStack itemStack){
        FluidStack fluidStack = FluidUtil.getFluidContained(itemStack);
        if(fluidStack!=null){
            return this.fluidList.contains(fluidStack.getFluid());
        }
        return false;
    }

    public PotProperty setName(String name){
        this.name = name;
        return this;
    }

    public PotProperty setSlotSize(int slotSize){
        this.slotSize = slotSize;
        return this;
    }

    public PotProperty setStackSize(int stackSize){
        this.stackSize = stackSize;
        return this;
    }

    public PotProperty setBlockList(List<Item> blockList){
        this.blockList = blockList;
        return this;
    }

    public String getName(){
        return this.name;
    }

    public int getSlotSize(){
        return this.slotSize;
    }

    public int getStackSize(){
        return this.stackSize;
    }

	public int getTankSize() {
		return tankSize;
	}

	public PotProperty setTankSize(int tankSize) {
		this.tankSize = tankSize;
		return this;
	}

	public int getVolumeSize() {
		return volumeSize;
	}

	public PotProperty setVolumeSize(int volumeSize) {
		this.volumeSize = volumeSize;
		return this;
	}

    public PotProperty setFluidList(List<Fluid> fluidList){
        this.fluidList = fluidList;
        return this;
    }

}
