package skily_leyu.mistyrain.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MRItemUtils {

    /**
     * 减少物品数量
     * @param player
     * @param itemStack
     * @param amount
     */
    public static void shrinkItemStack(EntityPlayer player, ItemStack itemStack, int amount){
        if(!player.isCreative()&&amount>0){
            itemStack.shrink(amount);
        }
    }

    /**
     * 放置物品并返回物品减少的数量，数量为0则表示物品没放进去
     * @param itemStackHandler 所要放置的物品栏
     * @param itemStack 所要放置的物品
     * @return int 返回需要减少的数量
     */
    public static int addItemInHandler(ItemStackHandler itemStackHandler, ItemStack itemStack){
        ItemStack copyItemStack = itemStack.copy();
        int itemAmount = copyItemStack.getCount();
        int itemMaxStackSize = copyItemStack.getMaxStackSize();

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

}
