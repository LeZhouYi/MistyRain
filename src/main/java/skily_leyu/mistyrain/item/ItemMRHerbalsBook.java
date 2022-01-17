package skily_leyu.mistyrain.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.client.gui.MRGuis;

public class ItemMRHerbalsBook extends Item{

    public ItemMRHerbalsBook(){
        this.setMaxStackSize(1);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        if (!worldIn.isRemote)
        {
            BlockPos pos = playerIn.getPosition();
            int id = MRGuis.GUI_MR_BOOK;
            playerIn.openGui(MistyRain.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
