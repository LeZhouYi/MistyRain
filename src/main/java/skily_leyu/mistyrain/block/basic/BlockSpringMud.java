package skily_leyu.mistyrain.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import skily_leyu.mistyrain.block.MRProperty;

/**
 * 春泥
 * @author Skily
 * @version 1.0.0
 */
public class BlockSpringMud extends Block{
    /**
     * state = 0, 普通的泥土状态
     * stage = 1，当上方无完整方法，非冬天，有草状态
     * stage = 2，当上方无完整方块，春夏天，有花状态
     * stage = 3, 当上方为有雪，且为冬天，有雪状态
     */
    public static final IProperty<Integer> STAGE = MRProperty.PART_STAGE;

    public BlockSpringMud(){
        super(Material.GROUND,MapColor.GREEN_STAINED_HARDENED_CLAY);
        this.setHardness(MRProperty.dirtHardness);
        this.setResistance(MRProperty.dirtHardness);
        this.setSoundType(SoundType.GROUND);
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
