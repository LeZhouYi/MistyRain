/**
 * 流青木根
 * @author Skily
 * @version 1.0.0
 */
public class BlockFctRoot {
    
    /**
     * 是否有分支，false = 没有分支， true = 有分支，方向根据FACING决定
     */
    public static final IProperty<Boolean> BRANCH = MRProperty.HAS_BRANCH;
    /**
     * 是否竖直，false=横着，此状态应无向下和向下的分支
     */
    public static final IProperty<Boolean> VERTICAL = MRProperty.IS_VERTICAL;
    public static final IProperty<EnumFacing> FACING = MRProperty.FACING;

    public BlockFctRoot(){

    }

    @Override
    public int getMetaFromState(IBlokState blockstate){
        return blockstate.getProperty(FACING).getHorizontalIndex()*4 
            + (blockstate.getProperty(VERTICAL)?1:0)*2;
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING,EnumFacing.plane.Horizontal[meta/4])
            .withProperty(VERTICAL,Boolean.valueOf(meta%4/2));
    }

}
