package skily_leyu.mistyrain.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import skily_leyu.mistyrain.feature.properties.MRProperties;

/**
 * 剑石
 * @author Skily
 * @version 1.0.0
 */
public class BlockMRSwordStone extends Block{

    public BlockMRSwordStone(){
        super(Material.GLASS,MapColor.BLACK);
        this.setHardness(MRProperties.obsidianHardness);
        this.setResistance(MRProperties.obsidianHardness);
        this.setSoundType(SoundType.STONE);
    }

}