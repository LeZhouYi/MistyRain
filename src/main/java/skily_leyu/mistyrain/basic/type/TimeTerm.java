package skily_leyu.mistyrain.basic.type;

/**
 * 时段
 * @author Skily
 * @version 1.0.0
 */
public enum TimeTerm{

	YeBan(23.0F,1.0F,"夜半"),
	JiMing(1.0F,3.0F,"鸡鸣"),
	PinDan(3.0F,5.0F,"平旦"),
	RiChu(5.0F,7.0F,"日出"),
	ShiShi(7.0F,9.0F,"食时"),
	YuZhong(9.0F,11.0F,"隅中"),
	RiZhong(11.0F,13.0F,"日中"),
	RiDie(13.0F,15.0F,"日昳"),
	BuShi(15.0F,17.0F,"晡时"),
	RiRu(17.0F,19.0F,"日入"),
	HuangHun(19.0F,21.0F,"黄昏"),
	RenDing(21.0F,23.0F,"人定");

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
			if(timeTerm!=TimeTerm.YeBan){
				if(now>timeTerm.getBegin()&&now<=timeTerm.getEnd()){
					return timeTerm;
				}
			}else{
				if(now>timeTerm.getBegin()||now<=timeTerm.getEnd()){
					return timeTerm;
				}
			}
		}
		return TimeTerm.YeBan;
	}

	//------------------getter and setter------------------------
	public float getBegin(){return this.begin;}
	public float getEnd(){return this.end;}
	public String getName(){return this.name;}

}