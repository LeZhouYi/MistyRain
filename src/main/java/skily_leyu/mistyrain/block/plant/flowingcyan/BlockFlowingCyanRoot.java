package skily_leyu.mistyrain.block.plant.flowingcyan;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skily_leyu.mistyrain.block.MRProperty;
import skily_leyu.mistyrain.block.define.BlockMRPlant;
import skily_leyu.mistyrain.utility.MRUtils;

/**
 * 流青木根
 * @author Skily
 * @version 1.0.0
 */
public class BlockFlowingCyanRoot extends BlockMRPlant{
    
    /**
     * 是否竖直，false=横着
     */
    public static final IProperty<Boolean> VERTICAL = MRProperty.IS_VERTICAL;
    public static final IProperty<EnumFacing> FACING = MRProperty.FACING;

    public BlockFlowingCyanRoot(){
        super(Material.WOOD, MapColor.GREEN_STAINED_HARDENED_CLAY);
        this.setHardness(MRProperty.woodHardness);
        this.setResistance(MRProperty.woodHardness);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(blockState.getBaseState().withProperty(VERTICAL, false).withProperty(FACING, EnumFacing.EAST));
        this.setTickRandomly(true);
    }

    @Override
    public int getMetaFromState(IBlockState blockstate){
        return blockstate.getValue(FACING).getHorizontalIndex()*4 
            + (blockstate.getValue(VERTICAL)?1:0)*2;
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING,EnumFacing.HORIZONTALS[meta/4])
            .withProperty(VERTICAL,Boolean.valueOf(meta%4/2==1));
    }

    @Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VERTICAL,FACING });
	}

    //-------------------Client Method or Property-------------------------------

    
    //-------------------Plant Event and Method-----------------------------
    @Override
    public boolean hasSupport(World worldIn, BlockPos pos, IBlockState state){
        if(state.getValue(VERTICAL)){
            return isSuitBlock(worldIn.getBlockState(pos.down()));
        }else{
            return isSuitBlock(worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())));
        }
    }

    @Override
    public boolean isSuitBlock(IBlockState blockstate){
//        return MRUtils.isPlantSoil(blockstate)||blockstate.getBlock()==MRBlocks.rainStone;
    	return true;
    }

    
    @Override
    public void destroy(World worldIn, Random rand, BlockPos pos, IBlockState state){
        super.destroy(worldIn,rand,pos,state);
        //掉落
        if(MRUtils.canDrop(rand)){
//            spawnAsEntity(worldIn, pos, new ItemStack(MRBlocks.fctLog));
        }
        //传递破坏消息
        if(state.getValue(VERTICAL)){
            MRUtils.spreadDestroy(worldIn,rand,pos.up(),worldIn.getBlockState(pos.up()));
        }else{
            BlockPos tePos = pos.offset(state.getValue(FACING));
            MRUtils.spreadDestroy(worldIn,rand,tePos,worldIn.getBlockState(tePos));
        }
    }


    /**
     * 监测依赖方块的变化
     */
    @Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        boolean flag = false;
        if(state.getValue(VERTICAL)){
            flag = fromPos==pos.down();
        }else{
            flag = fromPos==pos.offset(state.getValue(FACING));
        }
        if(flag){
            checkSupport(worldIn,worldIn.rand,pos,state);
        }
    }

}
