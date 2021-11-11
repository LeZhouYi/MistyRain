package skily_leyu.mistyrain.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import skily_leyu.mistyrain.feature.property.MRProperty;

/**
 * 水石
 * @author Skily
 * @version 1.0.0
 */
public class BlockWaterStone extends Block{

    public BlockWaterStone(){
        super(Material.GLASS,MapColor.BLUE);
        this.setHardness(MRProperty.stoneHardness);
        this.setResistance(MRProperty.dirtHardness);
        this.setSoundType(SoundType.GLASS);
    }

}
