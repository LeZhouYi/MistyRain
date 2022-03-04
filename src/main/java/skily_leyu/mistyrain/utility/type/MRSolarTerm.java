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

    public float getBegin(){
        return this.start;
    }

    public String getI18nKey(){
        return String.format("solarTerms.%d.name", this.ordinal());
    }

    /**
     * 设置当前世界时间至下一个节气
     * @param world
     */
    public static void setNextSolarTerm(World world){
        float now = getNowDays(world); //获取当前月天
        System.out.println("first"+getSolarTerm(now).ordinal());
        int nextOrdinal = (getSolarTerm(now).ordinal()+1)%MRSolarTerm.values().length; //获取下一个节气序值
        System.out.println("next"+nextOrdinal);
        float nextBegin = MRSolarTerm.values()[nextOrdinal].getBegin(); //获取下一个节气开始的月/天
        float needAdd = (nextBegin>=now)?(nextBegin-now+0.1F):(nextBegin+11.1F); //需增加的差值
        System.out.println("add"+(long)(needAdd*30.0F*24000F));
        long tickAdd = (long)(needAdd*30.0F*24000F); //增加的Tick数
        world.setTotalWorldTime(world.getTotalWorldTime()+tickAdd);
    }

    /**
     * 获取当前月/天对应的节气
     * @param now
     * @return
     */
    public static MRSolarTerm getSolarTerm(float now){
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
        float now = getNowDays(world);
        return getSolarTerm(now);
    }

    /**
     * 获取月份
     * @param world
     * @return
     */
    public static Month getMonth(World world){
        float now = getNowDays(world);
        return Month.values()[((int)now)-1];
    }

    /**
     * 获取上中下旬
     * @param world
     * @return
     */
    public static TimeSpan getTimeSpan(World world){
        float now = getNowDays(world);
        now = now - ((int)now); //取小数部分
        return (now<1/3.0F)?TimeSpan.FRONT:(now>2/3.0F?TimeSpan.BEHIND:TimeSpan.MIDDLE);
    }

    /**
     * 获取当时月/天数
     * @param world
     * @return
     */
    public static float getNowDays(World world){
        int days = (int)(world.getTotalWorldTime()/24000) + ((world.getTotalWorldTime()%24000==0)?0:1);
        float dayNow = 0.0F+(days)/30.0F;
        float now = ((int)dayNow)%12+1+(dayNow-(int)(dayNow)); //规整
        return now;
    }

    public static enum Season{
        SPRING,
        SUMMER,
        AUTUMN,
        WINTER;
    }

    public static enum Month{
        JAN,
        FEB,
        MAR,
        APR,
        MAY,
        JUN,
        JUL,
        AUG,
        SEPT,
        OCT,
        NOV,
        DEC;

        public String getI18nKey(){
            return String.format("month.%d.name", this.ordinal());
        }

    }

    public static enum TimeSpan{
        FRONT,
        MIDDLE,
        BEHIND;

        public String getI18nkey(){
            return String.format("timespan.%d.name", this.ordinal());
        }
    }

}