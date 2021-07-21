package skily_leyu.mistyrain.basic;

public enum SolarTerm{

	BeginningOfSpring("立春"),
	RainWater("雨水"),
	InsectsAwakening("惊蛰"),
	SpringEquinox("春分"),
	FreshGreen("清明"),
	GrainRain("谷雨"),
	BeginningOfSummer("立夏"),
	LesserFullness("小满"),
	GrainInEar("芒种"),
	SummerSolstice("夏至"),
	LesserHeat("小暑"),
	GreaterHeat("大暑"),
	BeginningOfAutumn("立秋"),
	EndOfHeat("处暑"),
	WhiteDew("白露"),
	AutumnalEquinox("秋分"),
	ColdDew("寒露"),
	FirstFrost("霜降"),
	BeginningOfWinter("立冬"),
	LightSnow("小雪"),
	HeavySnow("大雪"),
	WinterSolstice("冬至"),
	LesserCold("小寒"),
	GreaterCold("大寒");

	private String name;
	private SolarTerm(String name){
		this.name = name;
	}

	//------------------getter and setter------------------------
	public String getName(){return this.name;}

}