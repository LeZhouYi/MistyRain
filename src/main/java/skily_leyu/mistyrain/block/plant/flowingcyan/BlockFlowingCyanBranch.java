package skily_leyu.mistyrain.block.plant.flowingcyan;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import skily_leyu.mistyrain.block.MRProperty;
import skily_leyu.mistyrain.block.define.BlockMRPlant;

public class BlockFlowingCyanBranch extends BlockMRPlant{

    public static final IProperty<Integer> STAGE = MRProperty.TWO_STAGE;
    public static final IProperty<Boolean> VERTICAL = MRProperty.IS_VERTICAL;
    public static final IProperty<EnumFacing> FACING = MRProperty.FACING; 

    public BlockFlowingCyanBranch(){
        super(Material.WOOD, MapColor.GREEN_STAINED_HARDENED_CLAY);
        this.setHardness(MRProperty.woodHardness);
        this.setResistance(MRProperty.woodHardness);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(blockState.getBaseState().withProperty(STAGE,0).withProperty(VERTICAL, false).withProperty(FACING, EnumFacing.EAST));
        this.setTickRandomly(true);
    }

    @Override
    public int getMetaFromState(IBlockState blockstate){
        return blockstate.getValue(FACING).getHorizontalIndex()*4 
            + (blockstate.getValue(VERTICAL)?1:0)*2 + (blockstate.getValue(STAGE));
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING,EnumFacing.HORIZONTALS[meta/4])
            .withProperty(VERTICAL,Boolean.valueOf(meta%4/2==1)).withProperty(STAGE, meta%2);
    }

    @Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { STAGE,VERTICAL,FACING });
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

}
