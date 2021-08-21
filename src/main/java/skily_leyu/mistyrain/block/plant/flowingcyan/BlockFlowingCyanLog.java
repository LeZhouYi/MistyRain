package skily_leyu.mistyrain.block.plant.flowingcyan;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skily_leyu.mistyrain.block.MRBlocks;
import skily_leyu.mistyrain.block.MRProperty;
import skily_leyu.mistyrain.block.define.BlockMRPlant;
import skily_leyu.mistyrain.utility.MRUtils;

public class BlockFlowingCyanLog extends BlockMRPlant{
    
    public static final IProperty<Boolean> BRANCH = MRProperty.HAS_BRANCH;
    public static final IProperty<Boolean> VERTICAL = MRProperty.IS_VERTICAL;
    public static final IProperty<EnumFacing> FACING = MRProperty.FACING;

    public BlockFlowingCyanLog(){
        super(Material.WOOD, MapColor.GREEN_STAINED_HARDENED_CLAY);
        this.setHardness(MRProperty.woodHardness);
        this.setResistance(MRProperty.woodHardness);
        this.setSoundType(SoundType.WOOD);
       this.setDefaultState(blockState.getBaseState().withProperty(BRANCH,false).withProperty(VERTICAL, false).withProperty(FACING, EnumFacing.EAST));
    }

    @Override
    public int getMetaFromState(IBlockState blockstate){
        return blockstate.getValue(FACING).getHorizontalIndex()*4 
            + (blockstate.getValue(VERTICAL)?1:0)*2 + (blockstate.getValue(BRANCH)?1:0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING,EnumFacing.HORIZONTALS[meta/4])
            .withProperty(VERTICAL,Boolean.valueOf(meta%4/2==1)).withProperty(BRANCH, Boolean.valueOf(meta%2==1));
    }

    @Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { BRANCH,VERTICAL,FACING });
	}

    //-------------------Client Method or Property-------------------------------


    //-------------------Plant Event and Method-----------------------------
    @Override
    public boolean hasSupport(World worldIn, BlockPos pos, IBlockState state){
        if(state.getValue(VERTICAL)){
            //从下向上
            return isSuitBlock(worldIn.getBlockState(pos.down()));
        }else{
            //对角
            return isSuitBlock(worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())));
        }
    }

    @Override
    public boolean isSuitBlock(IBlockState blockstate){
//        return blockstate.getBlock()==this||blockstate.getBlock()==MRBlocks.flowingCyanRoot||blockstate.getBlock()==MRBlocks.flowingCyanBranch||blockstate.getBlock()==MRBlocks.flowingCyanBranch;
    	return true;
    }

    @Override
    public void destroy(World worldIn, Random rand, BlockPos pos, IBlockState state){
        super.destroy(worldIn,rand,pos,state);
        //掉落
        if(MRUtils.canDrop(rand)){
            spawnAsEntity(worldIn, pos, new ItemStack(this));
        }
        //传递破坏消息
        if(state.getValue(VERTICAL)){
            //从下向上
            MRUtils.spreadDestroy(worldIn,rand,pos.up(),worldIn.getBlockState(pos.up()));
            if(state.getValue(BRANCH)){
                BlockPos tePos = pos.offset(state.getValue(FACING));
                MRUtils.spreadDestroy(worldIn,rand,tePos,worldIn.getBlockState(tePos));
            }
        }else{
            //对角，分支在左侧
            EnumFacing teFacing = state.getValue(FACING);
            BlockPos tePos = pos.offset(teFacing);
            MRUtils.spreadDestroy(worldIn,rand,tePos,worldIn.getBlockState(tePos));
            tePos.offset(MRUtils.nextHorizontal(teFacing,true));
            MRUtils.spreadDestroy(worldIn,rand,tePos,worldIn.getBlockState(tePos));
        }
    }

}
