package skily_leyu.mistyrain.basic.type;

/**
 * 时令
 * @author Skily
 * @version 1.0.0
 */
public enum SolarTerm{

	BeginningOfSpring("立春",2.13F,2.63F),
	RainWater("雨水",2.63F,3.17F),
	InsectsAwakening("惊蛰",3.17F,3.67F),
	SpringEquinox("春分",3.67F,4.17F),
	FreshGreen("清明",4.17F,4.67F),
	GrainRain("谷雨",4.67F,5.20F),
	BeginningOfSummer("立夏",5.20F,5.70F),
	LesserFullness("小满",5.70F,6.20F),
	GrainInEar("芒种",6.20F,6.70F),
	SummerSolstice("夏至",6.70F,6.23F),
	LesserHeat("小暑",7.23F,7.77F),
	GreaterHeat("大暑",7.77F,8.27F),
	BeginningOfAutumn("立秋",8.27F,8.77F),
	EndOfHeat("处暑",8.77F,9.27F),
	WhiteDew("白露",9.27F,9.77F),
	AutumnalEquinox("秋分",9.77F,10.27F),
	ColdDew("寒露",10.27F,10.77F),
	FirstFrost("霜降",10.77F,11.27F),
	BeginningOfWinter("立冬",11.27F,11.73F),
	LightSnow("小雪",11.73F,12.23F),
	HeavySnow("大雪",12.23F,12.73F),
	WinterSolstice("冬至",12.73F,1.20F),
	LesserCold("小寒",1.20F,1.67F),
	GreaterCold("大寒",1.67F,2.13F);

	private String name;
	private float start;
	private float end;

	private SolarTerm(String name, float start, float end){
		this.name = name;
		this.start = start;
		this.end = end;
	}

	/**
	 * 根据value返回对应的SolarTerm,若value不对应则默认返回冬至
	 * @param value [1.0F,13.0F] 整数部分代表月份，小数部分代表当月第几天/当月天数
	 * @return SolorTerm 返回对应的节气，没有对应的默认返回冬至
	 */
	public static SolarTerm getSolarTerm(float value){
		for(SolarTerm solarTerm:SolarTerm.values()){
			if(solarTerm!=SolarTerm.WinterSolstice){
				if(value>=solarTerm.getStart()&&value<solarTerm.getEnd()){
					return solarTerm;
				}
			}else{
				if(value>=solarTerm.getStart()||value<solarTerm.getEnd()){
					return solarTerm;
				}
			}
		}
		return SolarTerm.WinterSolstice;
	}

	//------------------getter and setter------------------------
	public String getName(){return this.name;}
	public float getStart(){return this.start;}
	public float getEnd(){return this.end;}

}