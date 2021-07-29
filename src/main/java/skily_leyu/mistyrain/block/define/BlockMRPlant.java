package skily_leyu.mistyrain.block.define;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import skily_leyu.mistyrain.block.define.IMRPlant;

/**
 * 具有生长特性的MR植物方法基类
 */
public abstract class BlockMRPlant extends Block implements IMRPlant{
    
    public BlockMRPlant(Material material, MapColor mapColor){
        super(material,mapColor);
    }

}
