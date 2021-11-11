package skily_leyu.mistyrain.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import skily_leyu.mistyrain.feature.property.MRProperty;

/**
 * 剑石
 * @author Skily
 * @version 1.0.0
 */
public class BlockSwordStone extends Block{

    public BlockSwordStone(){
        super(Material.GLASS,MapColor.BLACK);
        this.setHardness(MRProperty.obsidianHardness);
        this.setResistance(MRProperty.obsidianHardness);
        this.setSoundType(SoundType.STONE);
    }

}