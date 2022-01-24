package skily_leyu.mistyrain.utility.type;

/**
 * 二十四节气
 */
public enum MRSolarTerm {

    SPRING_BEGINS(2.1F,2.6F),
    THE_RAINS(2.6F,3.17F),
    INSECTS_AWAKEN(3.17F,3.67F),
    VERNAL_EQUINOX(3.67F,4.17F),
    CLEAR_AND_BRIGHT(4.17F,4.63F),
    GRAIN_RAIN(4.63F,5.17F),
    SUMMER_BEGINS(5.17F,5.67F),
    GRAIN_BUDS(5.67F,6.17F),
    GRAIN_IN_EAR(6.17F,6.7F),
    SUMMER_SOLSTICE(6.7F,7.23F),
    SLIGHT_HEAT(7.23F,7.73F),
    GREAT_HEAT(7.73F,8.2F),
    AUTUMN_BEGINS(8.2F,8.73F),
    STOPPING_THE_HEAT(8.73F,9.23F),
    WHITE_DEWS(9.23F,9.73F),
    AUTUMN_EQUINOX(9.73F,10.23F),
    COLD_DEWS(10.23F,10.77F),
    HOAR_FROST_FALLS(10.77F,11.23F),
    WINTER_BEGINS(11.23F,11.73F),
    LIGHT_SNOW(11.73F,12.23F),
    HEAVY_SNOW(12.23F,12.73F),
    WINTER_SOLSTICE(12.73F,1.17F),
    SLIGHT_COLD(1.17F,1.63F),
    GREAT_COLD(1.63F,2.1F);

    private float start;
    private float end;

    private MRSolarTerm(float start, float end){
        this.start = start;
        this.end = end;
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

}