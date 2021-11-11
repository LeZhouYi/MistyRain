package skily_leyu.mistyrain.block.pot;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.EnumFacing;
import skily_leyu.mistyrain.feature.property.MRProperty;

public class MRLogPot extends Block{

    public static final IProperty<EnumFacing> FACING = MRProperty.FACING;

    public MRLogPot() {
        super(Material.WOOD,MapColor.GREEN);
    }

}