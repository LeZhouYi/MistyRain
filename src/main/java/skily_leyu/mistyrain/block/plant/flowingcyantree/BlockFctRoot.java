/**
 * 流青木根
 * @author Skily
 * @version 1.0.0
 */
public class BlockFctRoot {
    
    public static final IProperty<Boolean> ALIVE = MRProperty.IS_ALIVE;
    public static final IProperty<Boolean> VERTICAL = MRProperty.IS_VERTICAL;
    public static final IProperty<EnumFacing> FACING = MRProperty.FACING;

    public BlockFctRoot(){

    }

    @Override
    public int getMetaFromState(IBlokState blockstate){
        return blockstate.getProperty(FACING).getHorizontalIndex()*4 
            + (blockstate.getProperty(VERTICAL)?1:0)*2
            + (blockstate.getProperty(ALIVE)?1:0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING,EnumFacing.plane.Horizontal[meta/4])
            .withProperty(VERTICAL,Boolean.valueOf(meta%4/2))
            .withProperty(IS_ALIVE,Boolean.valueOf(meta%2));
    }

}
