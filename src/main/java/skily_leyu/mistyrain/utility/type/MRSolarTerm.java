package skily_leyu.mistyrain.utility.type;

import net.minecraft.world.World;

/**
 * 二十四节气
 */
public enum MRSolarTerm {

    SPRING_BEGINS(2.1F,2.6F,Season.SPRING),
    THE_RAINS(2.6F,3.17F,Season.SPRING),
    INSECTS_AWAKEN(3.17F,3.67F,Season.SPRING),
    VERNAL_EQUINOX(3.67F,4.17F,Season.SPRING),
    CLEAR_AND_BRIGHT(4.17F,4.63F,Season.SPRING),
    GRAIN_RAIN(4.63F,5.17F,Season.SPRING),
    SUMMER_BEGINS(5.17F,5.67F,Season.SUMMER),
    GRAIN_BUDS(5.67F,6.17F,Season.SUMMER),
    GRAIN_IN_EAR(6.17F,6.7F,Season.SUMMER),
    SUMMER_SOLSTICE(6.7F,7.23F,Season.SUMMER),
    SLIGHT_HEAT(7.23F,7.73F,Season.SUMMER),
    GREAT_HEAT(7.73F,8.2F,Season.SUMMER),
    AUTUMN_BEGINS(8.2F,8.73F,Season.AUTUMN),
    STOPPING_THE_HEAT(8.73F,9.23F,Season.AUTUMN),
    WHITE_DEWS(9.23F,9.73F,Season.AUTUMN),
    AUTUMN_EQUINOX(9.73F,10.23F,Season.AUTUMN),
    COLD_DEWS(10.23F,10.77F,Season.AUTUMN),
    HOAR_FROST_FALLS(10.77F,11.23F,Season.AUTUMN),
    WINTER_BEGINS(11.23F,11.73F,Season.WINTER),
    LIGHT_SNOW(11.73F,12.23F,Season.WINTER),
    HEAVY_SNOW(12.23F,12.73F,Season.WINTER),
    WINTER_SOLSTICE(12.73F,1.17F,Season.WINTER),
    SLIGHT_COLD(1.17F,1.63F,Season.WINTER),
    GREAT_COLD(1.63F,2.1F,Season.WINTER);

    private float start;
    private float end;
    private Season season;

    private MRSolarTerm(float start, float end, Season season){
        this.start = start;
        this.end = end;
        this.season = season;
    }

    public Season getSeason(){
        return this.season;
    }

    /**
     * 返回当天对应节气
     * @param now
     * @return
     */
    public static MRSolarTerm getSolarTerm(float now){
        now = ((int)now-1)%12+1+(now-(int)(now)); //规整
        for( MRSolarTerm term:values()){
            if(now>=term.start&&now<term.end){
                return term;
            }
        }
        return MRSolarTerm.WINTER_SOLSTICE; //均不符合时只有此节气符合
    }

    /**
     * 返回当前世界时间对应的节气
     * @param world
     * @return
     */
    public static MRSolarTerm getSolarTerm(World world){
        int days = (int)(world.getTotalWorldTime()/24000) + ((world.getTotalWorldTime()%24000==0)?0:1);
        float dayNow = 0.0F+(days)/30.0F;
        return getSolarTerm(dayNow);
    }

    public static enum Season{
        SPRING,
        SUMMER,
        AUTUMN,
        WINTER;
    }

}