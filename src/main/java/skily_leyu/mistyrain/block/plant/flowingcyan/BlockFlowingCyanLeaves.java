package skily_leyu.mistyrain.block.plant.flowingcyan;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skily_leyu.mistyrain.block.MRBlocks;
import skily_leyu.mistyrain.block.MRProperty;
import skily_leyu.mistyrain.block.define.BlockMRPlant;
import skily_leyu.mistyrain.utility.MRUtils;

public class BlockFlowingCyanLeaves extends BlockMRPlant{

    /**
     * stage = 0, 普通状态
     * stage = 1, 开花状态
     */
    public static final IProperty<Integer> STAGE = MRProperty.TWO_STAGE;

    public BlockFlowingCyanLeaves(){
        super(Material.LEAVES, MapColor.GREEN);
        this.setHardness(MRProperty.leavesHardness);
        this.setResistance(MRProperty.leavesHardness);
        this.setSoundType(SoundType.PLANT);
        this.setDefaultState(blockState.getBaseState().withProperty(STAGE,0));
        this.setTickRandomly(true);
    }

    @Override
    public int getMetaFromState(IBlockState blockstate){
        return (blockstate.getValue(STAGE));
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(STAGE, meta%2);
    }

    @Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { STAGE });
	}

    //-------------------Client Method or Property-------------------------------


    //-------------------Plant Event and Method-----------------------------

    @Override
    public boolean hasSupport(World worldIn, BlockPos pos, IBlockState state){
        return listCheckSupport(worldIn,pos,EnumFacing.UP,2);
    }

    @Override
    public boolean isSuitBlock(IBlockState blockstate){
//        return blockstate.getBlock()==MRBlocks.flowingCyanBranch||blockstate.getBlock()==MRBlocks.flowingCyanCross||blockstate.getBlock()==MRBlocks.flowingCyanLog;
    	return true;
    }

    /**
     * 深度遍历检测是否存在依赖
     * @param worldIn 方块存在维度
     * @param pos 当前处理的方块位置
     * @param teFacing 上层的迭代方向
     * @param time 一般指迭代的深度，建议取较小值<=3
     */
    public boolean listCheckSupport(World worldIn, BlockPos pos,EnumFacing teFacing, int time){
        //到达迭代深度
        if(time<=0){
            return false;
        }
        //非此方块不进行迭代
        IBlockState state = worldIn.getBlockState(pos);
        if(state.getBlock()!=this){
            return false;
        }
        //检测是否存在依赖
        if(isSuitBlock(state)){
            return true;
        }else{
            //深层遍历，忽略向上遍历
            for(EnumFacing facing:EnumFacing.values()){
                //避免循环迭代
                if(facing!=EnumFacing.UP&&facing!=teFacing){
                    BlockPos tePos = pos.offset(facing);
                    IBlockState teState = worldIn.getBlockState(tePos);
                    if(teState.getBlock()==this&&listCheckSupport(worldIn,tePos,teFacing,time-1)){
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public void destroy(World worldIn, Random rand, BlockPos pos, IBlockState state){
        super.destroy(worldIn,rand,pos,state);
        //传递破坏消息
        for(EnumFacing facing:EnumFacing.values()){
            if(facing!=EnumFacing.DOWN){
                BlockPos tePos = pos.offset(facing);
                IBlockState teState = worldIn.getBlockState(tePos);
                if(teState.getBlock()==this){
                    MRUtils.spreadDestroy(worldIn,rand,tePos,teState);
                }
            }
        }
    }

}
