package skily_leyu.mistyrain.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import skily_leyu.mistyrain.block.MRProperty;

/**
 * 雨石
 * @author Skily
 * @version 1.0.0
 */
public class BlockRainStone extends Block{

    /**
     * 0 = 普通的石头状态
     * 1 = 有植物依附侧边时，侧面有草状态
     * 2 = 上方无完整方块，非冬，有草状态
     * 3 = 上方有雪时，冬天，有雪状态
     */
    public static final IProperty<Integer> STAGE = MRProperty.PART_STAGE;

    public BlockRainStone(){
        super(Material.ROCK,MapColor.BLUE_STAINED_HARDENED_CLAY);
        this.setHardness(MRProperty.stoneHardness);
        this.setResistance(MRProperty.stoneHardness);
        this.setSoundType(SoundType.STONE);
        this.setDefaultState(blockState.getBaseState().withProperty(STAGE,0));
        this.setTickRandomly(true);
    }

    @Override
    public int getMetaFromState(IBlockState blockstate){
        return blockstate.getValue(STAGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(STAGE,meta%4);
    }
    

}