package skily_leyu.mistyrain.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skily_leyu.mistyrain.block.MRBlocks;
import skily_leyu.mistyrain.feature.properties.CanProperties;
import skily_leyu.mistyrain.feature.properties.property.CanProperty;

public class ItemMRWoodenWaterCan extends ItemBlock{

    public ItemMRWoodenWaterCan() {
        super(MRBlocks.woodenWaterCan);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(raytraceresult != null && itemstack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)){
            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK){
                IBlockState blockState = worldIn.getBlockState(raytraceresult.getBlockPos());
                System.out.print(blockState.getBlock().getUnlocalizedName());
                IFluidHandlerItem handler = itemstack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);

                Fluid fluid = CanProperties.getCollectWater(blockState.getBlock());
                if (fluid!=null){
                    //取水操作
                    CanProperty canProperty = CanProperties.WOODEN_NORMAL;
                    if(canProperty.containsFluid(fluid)){
                        if(handler.fill(new FluidStack(fluid,canProperty.getVolumePerCollect()), true)>0){
                            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
                        }
                    }
                }else if(blockState.getBlock()==MRBlocks.woodenPot){
                    //浇水操作
                    playerIn.setActiveHand(handIn);
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
                }
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
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
        if(stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)){
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            CanProperty canProperty = CanProperties.WOODEN_NORMAL;
            FluidStack fluidStack = handler.drain(canProperty.getVolume(), false);
            if(fluidStack!=null) {
                return canProperty.calculateDuration(fluidStack.amount);
            }
        }
        return 0;
    }

    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.BOW;
    }

    @Nullable
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt){
        if(stack.getItem() == this){
            CanProperty canProperty = CanProperties.WOODEN_NORMAL;
            return new FluidHandlerItemStack(stack, canProperty.getVolume());
        }
        return stack;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)){
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            CanProperty canProperty = CanProperties.WOODEN_NORMAL;
            FluidStack fluidStack = handler.drain(canProperty.getVolume(), false);
            if(fluidStack!=null){
                tooltip.add(fluidStack.getUnlocalizedName()+":"+fluidStack.amount+"ml");
            }
        }
        // NBTTagList nbttaglist = getEnchantments(stack);

        // for (int i = 0; i < nbttaglist.tagCount(); ++i)
        // {
        //     NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
        //     int j = nbttagcompound.getShort("id");
        //     Enchantment enchantment = Enchantment.getEnchantmentByID(j);

        //     if (enchantment != null)
        //     {
        //         tooltip.add(enchantment.getTranslatedName(nbttagcompound.getShort("lvl")));
        //     }
        // }
    }

}
