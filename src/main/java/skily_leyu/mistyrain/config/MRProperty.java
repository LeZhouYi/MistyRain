package skily_leyu.mistyrain.config;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

public class MRProperty {

    public static final IProperty<EnumFacing> FACING_HORIZONTAL = PropertyDirection.create("facing",EnumFacing.Plane.HORIZONTAL);

}
