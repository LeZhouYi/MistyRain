package skily_leyu.mistyrain.block.define;

/**
 * 标记方块生长的状态
 * @author Skily
 * @version 1.0.0
 */
public enum PlantEvent {
    /**停止生长，保持原状态 */
    STOP,
    /**进行生长方法 */
    GROW,
    /**进入枯萎方法 */
    DECAY,
    /**进入破坏方法 */
    DESTROY;
}
