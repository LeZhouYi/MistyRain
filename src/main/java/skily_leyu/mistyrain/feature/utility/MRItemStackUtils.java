package skily_leyu.mistyrain.feature.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

/**
 * @author skily_leyu
 * 物品操作相关的一些通用方法，包括对物品，物品栏的处理
 */
public class MRItemStackUtils {

    /**
     * 放置物品并返回物品减少的数量，数量为0则表示物品没放进去
     * @param itemStackHandler 所要放置的物品栏
     * @param itemStack 所要放置的物品
     * @return int 返回需要减少的数量
     */
    public static int addItemInItemStackHandler(ItemStackHandler itemStackHandler, ItemStack itemStack){
        int itemAmount = itemStack.getCount();
        int itemMaxStackSize = itemStack.getMaxStackSize();
        ItemStack copyItemStack = itemStack.copy();

        for(int index = 0; index<itemStackHandler.getSlots()&&itemAmount>0;index++){
            ItemStack slotStack = itemStackHandler.getStackInSlot(index);
            int maxStackSize = MRMathUtils.minInteger(itemStackHandler.getSlotLimit(index),itemMaxStackSize);

            if(slotStack.isEmpty()){

                int addAmount = MRMathUtils.minInteger(maxStackSize, itemAmount);//计算应放数量
                copyItemStack.setCount(addAmount);
                itemStackHandler.setStackInSlot(index, copyItemStack); //放置
                itemAmount-=addAmount; //计算剩余

            }else if(slotStack.getItem()==itemStack.getItem()){

                int itemsurplusAmount = maxStackSize-slotStack.getCount();
                if(itemsurplusAmount>0){ //还能继续存放
                    int addAmount = MRMathUtils.minInteger(itemsurplusAmount, itemAmount); //计算应放数量
                    copyItemStack.setCount(slotStack.getCount()+addAmount);
                    itemStackHandler.setStackInSlot(index, copyItemStack); //追加放置
                    itemAmount-=addAmount;
                }
            }
        }
        return itemStack.getCount()-itemAmount; //返回应减少的数量
    }

    /**
     * 非创造模式下减少对应物品的数量
     * @param player
     * @param itemsStack
     * @param shrinkQuantity
     */
    public static void shrinkItemStack( EntityPlayer player,ItemStack itemsStack,int shrinkQuantity){
        if(!player.isCreative()){
            itemsStack.shrink(shrinkQuantity);
        }
    }

}
