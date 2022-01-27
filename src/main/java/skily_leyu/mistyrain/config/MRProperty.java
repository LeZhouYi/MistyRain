package skily_leyu.mistyrain.config;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.util.EnumFacing;

public class MRProperty {

    /**Properties */
    public static final IProperty<EnumFacing> FACING_HORIZONTAL = PropertyDirection.create("facing",EnumFacing.Plane.HORIZONTAL);
    public static final PropertyInteger PLANT_STAGE_HEX = PropertyInteger.create("plant_stage", 0, 15);

    /**MRPot key的取值 */
    public static final String WOODEN_BASE = "wooden_base";
}