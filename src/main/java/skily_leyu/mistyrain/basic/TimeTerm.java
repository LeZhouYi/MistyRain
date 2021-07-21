package skily_leyu.mistyrain.basic;

public enum TimeTerm{

	BeforeDawn(1,5,"凌晨"),
	Dawn(5,7,"黎明"),
	Morning(7,11,"早晨"),
	Noon(11,14,"中午"),
	AfterNoon(14,17,"午后"),
	Dusk(17,19,"黄昏"),
	Night(19,23,"夜晚"),
	MidNight(23,1,"深夜");

	private int begin;
	private int end;
	private String name;

	private TimeTerm(int begin, int end, String name){
		this.begin = begin;
		this.end = end;
		this.name = name;
	}

	//------------------getter and setter------------------------
	public int getBegin(){return this.begin;}
	public int getEnd(){return this.end;}
	public String getName(){return this.name;}

}