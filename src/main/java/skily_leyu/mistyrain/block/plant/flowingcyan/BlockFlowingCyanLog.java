package skily_leyu.mistyrain.block.plant.flowingcyan;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import skily_leyu.mistyrain.block.MRProperty;
import skily_leyu.mistyrain.block.define.BlockMRPlant;

public class BlockFlowingCyanLog extends BlockMRPlant{
    
    public BlockFlowingCyanLog(){
        super(Material.WOOD, MapColor.GREEN_STAINED_HARDENED_CLAY);
        this.setHardness(MRProperty.woodHardness);
        this.setResistance(MRProperty.woodHardness);
        this.setSoundType(SoundType.WOOD);
//        this.setDefaultState(blockState.getBaseState().withProperty(VERTICAL, false).withProperty(FACING, EnumFacing.EAST));
    }

}
