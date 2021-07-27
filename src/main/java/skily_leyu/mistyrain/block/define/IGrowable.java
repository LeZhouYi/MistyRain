package skily_leyu.mistyrain.block.define;

/**
 * 使用原版生长的一些特性基础上，增加一些自身方便进行代码组织的方法
 * @author Skily
 * @version 1.0.0
 */
public interface IGrowable {

    /**
     * 方块的生长方法
     */
    void grow();

    /**
     * 方块的枯萎方法
     */
    void decay();

    /**
     * 检测方法的生长状态
     */
    GrowType canGrow();

}