package skily_leyu.mistyrain.block.pot;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skily_leyu.mistyrain.feature.property.MRProperty;
import skily_leyu.mistyrain.feature.property.PotProperties;
import skily_leyu.mistyrain.feature.utility.MRItemStackUtils;
import skily_leyu.mistyrain.tileentity.MRTileEntityPot;

public class BlockMRWoodenPot extends Block implements ITileEntityProvider{

    public static final IProperty<EnumFacing> FACING = MRProperty.FACING;

    public static final AxisAlignedBB POT_AABB = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.4375D, 0.8125D);

    public BlockMRWoodenPot() {
        super(Material.WOOD,MapColor.GREEN);
    }

    @Override
    public int getMetaFromState(IBlockState blockstate){
        return blockstate.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING,EnumFacing.HORIZONTALS[meta]);
    }

    @Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

    //----------------------Block Property---------------------------------

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return POT_AABB;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
    	return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    	if(EnumFacing.DOWN!=face) {
    		return BlockFaceShape.UNDEFINED;
    	}
    	return BlockFaceShape.CENTER;
    }

    //----------------------Client or Render Method-------------------------

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    //----------------------Core or Custom Method---------------------------
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
    		float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
    	return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new MRTileEntityPot().init(PotProperties.WOOD_NORMAL);
	}

    /**
     * 主手手持泥土右键添加泥土
     */
    @Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(hand==EnumHand.MAIN_HAND){
            ItemStack mainItemStack = playerIn.getHeldItemMainhand();
            if(!mainItemStack.isEmpty()){
                MRTileEntityPot potTileEntity = getTileEntity(worldIn,pos);
                if(potTileEntity==null){
                    return false;
                }
                if(potTileEntity.isSoil(mainItemStack)){//属于泥土，放置泥土
                    MRItemStackUtils.shrinkItemStack(playerIn, mainItemStack, potTileEntity.addSoil(mainItemStack));
                    potTileEntity.markDirty();
                    return true;
                }
            }
        }
        return false;
	}

    @Nullable
    private MRTileEntityPot getTileEntity(World worldIn, BlockPos pos){
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity instanceof MRTileEntityPot ? (MRTileEntityPot)tileentity : null;
    }

}