package skily_leyu.mistyrain.utility.type;

import java.util.List;
import java.util.Map;
import java.util.Random;

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
    protected List<PlantPlan> plans; //植物的生长计划


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
        private List<Integer> weights; //转换权重,以1000为分母，weights[0]为第一个权重，weigts[1]-weigths为第二个权重
        private List<MRSolarTerm> term; //0=开始时间，1=结束时间

        public PlantStage getNowPlantStage(){
            return this.nowStage;
        }

        /**
         * 判断是否是合适的生长期
         * @param term
         * @return
         */
        public boolean isSuitTerm(MRSolarTerm term){
            int start = this.term.get(0).ordinal();
            int end = this.term.get(1).ordinal();
            int now = term.ordinal();
            return (start<=now&&now<=end)||(now>=start&&start>end);
        }

        /**
         * 返回当前植物状态要转换的状态
         * @param rand
         * @return
         */
        public PlantStage getToStage(Random rand){
            if(toStage.size()>1){
                int randValue = rand.nextInt(1000);
                for(int i = 0;i<toStage.size();i++){
                    if(weights.get(i)>randValue){
                        return toStage.get(i);
                    }
                }
            }
            return toStage.get(0);
        }

    }

}
