package skily_leyu.mistyrain.utility.type;

public class MRAnimal {

    private AnimalType animalType;
    private AnimaLevel animaLevel;

    public MRAnimal(AnimalType animalType,AnimaLevel animaLevel){
        this.animalType=animalType;
        this.animaLevel=animaLevel;
    }

    /**
     * 根据输入数返回对应的等级，1-2不降级，3-4降一级，5及以上降两级
     * @param output
     * @return
     */
    public AnimaLevel getAnimaLevel(int output){
        int decrease = (output<3)?0:((output<5)?1:2);
        int index = this.getAnimaLevel().ordinal()-decrease;
        index = (index<0)?0:index;
        return AnimaLevel.values()[index];
    }

    public AnimalType getAnimalType(){
        return this.animalType;
    }

    public AnimaLevel getAnimaLevel(){
        return this.animaLevel;
    }

    /**灵气的等级 */
    public static enum AnimaLevel {
        EMPTY,
        THIN,
        FILLING,
        RICH;

        public String getI18nFormat(){
            return "anima.level."+this.ordinal();
        }

        public int getLevel(){
            return this.ordinal();
        }

    }

    /**灵气的种类 */
    public static enum AnimalType {
        EMPTY,
        DREAM,
        MATAL,
        WOOD,
        WATER,
        FIRE,
        EARTH;

        public String getI18nFormat(){
            return "anima.level."+this.ordinal();
        }

    }

}