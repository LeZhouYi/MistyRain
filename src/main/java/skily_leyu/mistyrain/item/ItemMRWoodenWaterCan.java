package skily_leyu.mistyrain.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skily_leyu.mistyrain.block.MRBlocks;

public class ItemMRWoodenWaterCan extends ItemBlock{

    public ItemMRWoodenWaterCan() {
        super(MRBlocks.woodenWaterCan);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(player.isSneaking()){
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        return EnumActionResult.SUCCESS;
    }

}
