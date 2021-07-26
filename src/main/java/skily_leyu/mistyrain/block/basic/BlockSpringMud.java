
/**
 * 春泥
 * @author Skily
 * @version 1.0.0
 */
public class BlockSpringMud {
    /**
     * state = 0, 普通的泥土状态
     * stage = 1，当上方无完整方法，非冬天，有草状态
     * stage = 2，当上方无完整方块，春夏天，有花状态
     * stage = 3, 当上方为有雪，且为冬天，有雪状态
     */
    public static final IProperty<Integer> STAGE = MRProperty.PART_STAGE;

    public BlockSpringMud(){
       this.setHardness(MRProperty.dirtHardness);
       this.setResistance(MRProperty.dirtHardness);
       this.setSoundType(SoundType.DIRT);
       this.setMaterial(Materil.DIRT);
       this.setDefaultState(blockstate.getDefaultState().withProperty(STAGE,0));
       this.setRandomTick(true);
    }

    @Override
    public int getMetaFromState(IBlokState blockstate){
        return blockstate.getProperty(STATE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(STAGE,meta%4);
    }

}
