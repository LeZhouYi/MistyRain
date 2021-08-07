package skily_leyu.mistyrain.block.plant.flowingcyan;

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
        this.setSoundType(SoundType.LEAVES);
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

    }

    listCheckSupport(World worldIn, BlockPos pos, IBlockState state, int time){
        if(time<=0){
            return false;
        }
        if(isSuitBlock(worldIn.getBlockState()))
    }

}
