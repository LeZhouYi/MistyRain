package skily_leyu.mistyrain.block.plant.flowingcyantree;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import skily_leyu.mistyrain.block.MRProperty;
import skily_leyu.mistyrain.block.define.BlockMRPlant;

/**
 * 流青木根
 * @author Skily
 * @version 1.0.0
 */
public class BlockFctRoot extends BlockMRPlant{
    
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
        super(Material.WOOD, MapColor.GREEN_STAINED_HARDENED_CLAY);
        this.setHardness(MRProperty.woodHardness);
        this.setResistance(MRProperty.woodHardness);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(blockState.getBaseState().withProperty(BRANCH, false).withProperty(VERTICAL, false).withProperty(FACING, EnumFacing.EAST));
    }

    @Override
    public int getMetaFromState(IBlockState blockstate){
        return blockstate.getValue(FACING).getHorizontalIndex()*4 
            + (blockstate.getValue(VERTICAL)?1:0)*2;
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING,EnumFacing.Plane.HORIZONTAL.facings()[meta/4])
            .withProperty(VERTICAL,Boolean.valueOf(meta%4/2==1));
    }

    @Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { BRANCH,VERTICAL,FACING });
	}

}
