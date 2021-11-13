package skily_leyu.mistyrain.feature.property;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PotProperty {

    private String name; //用于标记和获取PotProty,大驼峰式
    private int slotSize; //设置花盆储存泥土的格数
    private int stackSize; //设置花盆单格储存泥土的数量
    private List<Item> whiteList; //可以放置的方块（土壤方块）

    public PotProperty(String name, int slotSize, int stackSize, List<Item> whiteList){
        this.name = name;
        this.slotSize = slotSize;
        this.stackSize = stackSize;
        this.whiteList = whiteList;
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

    public boolean containsSoil(ItemStack itemStack){
        return this.whiteList.contains(itemStack.getItem());
    }

}
