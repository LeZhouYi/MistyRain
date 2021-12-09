package skily_leyu.mistyrain.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import skily_leyu.mistyrain.feature.properties.MRProperties;

/**
 * 水石
 * @author Skily
 * @version 1.0.0
 */
public class BlockMRWaterStone extends Block{

    public BlockMRWaterStone(){
        super(Material.GLASS,MapColor.BLUE);
        this.setHardness(MRProperties.stoneHardness);
        this.setResistance(MRProperties.dirtHardness);
        this.setSoundType(SoundType.GLASS);
    }

}
