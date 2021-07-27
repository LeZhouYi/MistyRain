package skily_leyu.mistyrain.basic.type;

/**
 * 时段
 * @author Skily
 * @version 1.0.0
 */
public enum TimeTerm{

	BeforeDawn(1.0F,5.0F,"凌晨"),
	Dawn(5.0F,7.0F,"黎明"),
	Morning(7.0F,11.0F,"早晨"),
	Noon(11.0F,14.0F,"中午"),
	AfterNoon(14.0F,17.0F,"午后"),
	Dusk(17.0F,19.0F,"黄昏"),
	Night(19.0F,23.0F,"夜晚"),
	MidNight(23.0F,1.0F,"深夜");

	private float begin;
	private float end;
	private String name;

	private TimeTerm(float begin, float end, String name){
		this.begin = begin;
		this.end = end;
		this.name = name;
	}

	/**
	 * 根据now来获取当天的时间段
	 * @param now [0.0F,24.0F]
	 * @return TimeTerm返回对应的时间段，若now值不正确，则返回深夜
	 */
	public static TimeTerm getTimeTerm(float now){
		for(TimeTerm timeTerm: TimeTerm.values()){
			if(timeTerm!=MidNight){
				if(now>timeTerm.getBegin()&&now<=timeTerm.getEnd()){
					return timeTerm;
				}
			}else{
				if(now>timeTerm.getBegin()||now<=timeTerm.getEnd()){
					return timeTerm;
				}
			}
		}
		return TimeTerm.MidNight;
	}

	//------------------getter and setter------------------------
	public float getBegin(){return this.begin;}
	public float getEnd(){return this.end;}
	public String getName(){return this.name;}

}