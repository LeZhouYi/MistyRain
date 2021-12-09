package skily_leyu.mistyrain.item;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import skily_leyu.mistyrain.block.MRBlocks;

public class ItemMRWoodenWaterCan extends ItemBlock{

    public ItemMRWoodenWaterCan() {
        super(MRBlocks.woodenWaterCan);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (raytraceresult == null){
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }
        else{
            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK){
                BlockPos blockpos = raytraceresult.getBlockPos();
                if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER){

                }else{
                    playerIn.setActiveHand(handIn);
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
                }
            }
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(player.isSneaking()){
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        return EnumActionResult.PASS;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        return 32;
    }

    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.BOW;
    }

    protected ItemStack onCollectWater(ItemStack itemStack){
        return itemStack;
    }

    @Nullable
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt){
        return new FluidHandlerItemStack(stack, 4000);
    }


}
