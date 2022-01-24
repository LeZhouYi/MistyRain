package skily_leyu.mistyrain.utility.type;

import java.util.List;
import java.util.Map;

import net.minecraft.util.EnumFacing;

public class MRPlant {

    /**植物对应的物品*/
    protected String registryItem;

    protected int[] light; //光照条件
    protected int[] temperature; //温度条件
    protected int[] humidity; //湿度条件
    protected int fertilizer; //肥料消耗
    protected int waterConsumption; //水份消耗

    protected List<MRAnimal> animals; //灵气所需

    protected Map<PlantStage,List<String>> productItems; //对应生长阶段的产物
    protected Map<PlantStage,MRAnimal> productAnimal; //灵气产物
    protected List<EnumFacing> facings; //灵气逸散方向

    /**
     * 植物生长状态
     */
    public static enum PlantStage{
        SEED_DROP,
        SPROUT,
        ROOT_DEPTH,
        VERDANT,
        IN_BUD,
        FULL_BLOWN,
        FRUIT,
        FRUIT_MATURE,
        LEAVES_YELLOW,
        LEAVES_FALL,
        FADE;
    }

    public static class PlantPlan{
        private PlantStage nowStage; //目前状态
        private List<PlantStage> toStage; //对应状态
        private List<Integer> weight; //转换权重
    }

}
