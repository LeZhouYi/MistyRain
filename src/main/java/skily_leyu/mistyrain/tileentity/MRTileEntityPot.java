package skily_leyu.mistyrain.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.feature.property.PotProperty;
import skily_leyu.mistyrain.feature.utility.MRItemStackUtils;

public class MRTileEntityPot extends TileEntity{

    protected ItemStackHandler soilInventory;
    protected PotProperty potProperty;

    public MRTileEntityPot(PotProperty potProperty){
        this.potProperty = potProperty;
        this.soilInventory = new ItemStackHandler(potProperty.getSlotSize()){
            @Override
            public int getSlotLimit(int slot) {
                return potProperty.getStackSize();
            }
        };
    }

    /**
     * 判断物品是否属于可放置的泥土
     * @param itemStack
     * @return
     */
    public boolean isSoil(ItemStack itemStack){
        return this.potProperty.containsSoil(itemStack);
    }

    /**
     * 添加泥土并返回放置数量，不处理物品数量的减少，不校验物品是否合法
     * @param itemStack
     * @return
     */
    public int addSoil(ItemStack itemStack){
        return MRItemStackUtils.addItemInItemStackHandler(this.soilInventory, itemStack);
    }

}