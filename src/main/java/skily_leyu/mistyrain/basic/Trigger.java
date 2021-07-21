package skily_leyu.mistyrain.basic;

import java.util.List;

public class Trigger{
	private String type;
	private List<Condition> conditions;
	private int dotIndex;

	//------------------getter and setter------------------------
	public String getType(){return this.type;}
	public List<Condition> getCondtions(){return this.conditions;}
	public int getDotIndex(){return this.dotIndex;}

}