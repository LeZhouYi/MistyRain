/**
 * 雨石
 * @author Skily
 * @version 1.0.0
 */
public class BlockRainStone{

    /**
     * 0 = 普通的石头状态
     * 1 = 有植物依附侧边时，侧面有草状态
     * 2 = 上方无完整方块，非冬，有草状态
     * 3 = 上方有雪时，冬天，有雪状态
     */
    public static final IProperty<Integer> STAGE = MRProperty.PART_STAGE;

    public BlockRainStone(){
        this.setHardness(MRProperty.stoneHardness);
        this.setResistance(MRProperty.stoneHardness);
        this.setSoundType(SoundType.STONE);
        this.setMaterial(Material.STONE);
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