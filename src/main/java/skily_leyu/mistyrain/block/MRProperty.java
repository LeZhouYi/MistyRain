package skily_leyu.mistyrain.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.util.EnumFacing;

/**
 * 集中一些用于配置的属性和IProperty
 * @author Skily
 * @version 1.0.0
 */
public class MRProperty {
    
    //------硬度相关的属性----------//
    public static float dirtHardness = 0.5F;
    public static float stoneHardness = 1.5F;
    public static float woodHardness = 2.0F;
    public static float obsidianHardness = 50.0F;

    /**用于基础的方块状态 */
    public static final IProperty<Integer> PART_STAGE = PropertyInteger.create("stage",0,3);

    /**是否有分支，一般为一分支 */
    public static final IProperty<Boolean> HAS_BRANCH = PropertyBool.create("has_branch");
    /**是否竖直状态 */
    public static final IProperty<Boolean> IS_VERTICAL = PropertyBool.create("is_vertical");
    /**是否为活着的状态，标记方块是否可以进行生长更新 */
    public static final IProperty<Boolean> IS_ALIVE = PropertyBool.create("is_alive");
    /**水平坐标系的东南西北 */
    public static final IProperty<EnumFacing> FACING = PropertyDirection.create("facing",EnumFacing.Plane.HORIZONTAL);

}
