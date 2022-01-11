package skily_leyu.mistyrain.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
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

    private CanProperty canProperty = CanProperties.WOODEN_NORMAL;

    public ItemMRWoodenWaterCan() {
        super(MRBlocks.woodenWaterCan);
        this.setMaxStackSize(1);
        this.setMaxDamage(canProperty.getVolume());
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
                    if(canProperty.containsFluid(fluid)){
                        int amount = handler.fill(new FluidStack(fluid,canProperty.getVolumePerCollect()), true);
                        if(amount>0){
                            itemstack.setItemDamage(itemstack.getItemDamage()+amount);
                            System.out.println(itemstack.getItemDamage());
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
        if(hand==EnumHand.MAIN_HAND){
            if(player.isSneaking()){
                return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        if(stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)){
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
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
            return new FluidHandlerItemStack(stack, canProperty.getVolume());
        }
        return stack;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count){
        if(count%20==0&&player.isActiveItemStackBlocking()&&stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)){
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            FluidStack fluidStack = handler.drain(canProperty.getVolumePerSecond(), true);
            if(fluidStack!=null){
                stack.damageItem(fluidStack.amount, player);
                System.out.println(stack.getItemDamage());
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        if(flagIn==ITooltipFlag.TooltipFlags.ADVANCED){
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            FluidStack fluidStack = handler.drain(canProperty.getVolume(), false);
            if(fluidStack!=null){
                tooltip.add(I18n.format("can.volume.info", fluidStack.amount,canProperty.getVolume()));
            }
        }
    }

}
