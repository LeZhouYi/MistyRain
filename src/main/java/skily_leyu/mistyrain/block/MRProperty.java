public class MRProperty {

    public static float dirtHardness = 0.5F;
    public static float stoneHardness = 1.5F;

    /**用于基础的方块状态 */
    public static final IProperty<Integer> PART_STAGE = PropertyInteger.create("stage",0,3);

    /**是否竖直状态 */
    public static final IProperty<Boolean> IS_VERTICAL = PropertyBoolean.create("is_vertical");
    /**是否为活着的状态，标记方块是否可以进行生长更新 */
    public static final IProperty<Boolean> IS_ALIVE = PropertyBoolean.create("is_alive");
    /**水平坐标系的东南西北 */
    public static final IProperty<EnumFacing> FACING = PropertyDirection.create("facing",EnumFacing.Plane.Horizontal);

}
