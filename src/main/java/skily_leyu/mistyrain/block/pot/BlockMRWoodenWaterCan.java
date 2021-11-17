package skily_leyu.mistyrain.block.pot;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
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

public class BlockMRWoodenWaterCan extends Block{

    public static final IProperty<EnumFacing> FACING = MRProperty.FACING;

    public static final AxisAlignedBB[] CAN_AABB = new AxisAlignedBB[]{
        new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.75D, 0.625D, 0.75D),
        new AxisAlignedBB(0.25D, 0.0D, 0.3125D, 0.6875D, 0.625D, 0.75D),
        new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.6875D, 0.625D, 0.6875D),
        new AxisAlignedBB(0.3125D, 0.0D, 0.25D, 0.75D, 0.625D, 0.6875D)
    };

    public BlockMRWoodenWaterCan() {
        super(Material.WOOD,MapColor.BROWN);
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
        return CAN_AABB[state.getValue(FACING).getHorizontalIndex()];
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

}
