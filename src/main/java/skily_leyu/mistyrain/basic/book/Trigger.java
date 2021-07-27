package skily_leyu.mistyrain.basic.book;

import java.util.List;

/**
 * 触发器的存储结构
 * @author Skily
 * @version 1.0.0
 */
public class Trigger{

	/**触发器的类型 */
	private String type;
	/**一系列条件,需满足全部条件 */
	private List<Condition> conditions;
	/**收集点在层集的列表中的顺序 */
	private int dotIndex;

	//------------------getter and setter------------------------
	public String getType(){return this.type;}
	public List<Condition> getCondtions(){return this.conditions;}
	public int getDotIndex(){return this.dotIndex;}

}