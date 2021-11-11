package skily_leyu.mistyrain.feature.property;

public class PotProperty {

    private int slotSize; //设置花盆储存泥土的格数
    private int stackSize; //设置花盆单格储存泥土的数量

    public PotProperty(int slotSize, int stackSize){
        this.slotSize = slotSize;
        this.stackSize = stackSize;
    }

    public int getSlotSize(){
        return this.slotSize;
    }

    public int getStackSize(){
        return this.stackSize;
    }

}
