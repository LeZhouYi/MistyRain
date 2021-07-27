package skily_leyu.mistyrain.basic.book;

/**
 * 触发器条件的存储结构
 * @author Skily
 * @version 1.0.0
 */
public class Condition{

	/**判断的类型 */
	private String type;
	/**用于判断的取值 */
	private String value;
	/**用于判断的额外取值 */
	private String extractValue;
	/**指示方块或物品的数量 */
	private int amount;
	/**指示方块或物品的基础属性 */
	private int metadata;
	
	//------------------getter and setter------------------------
	public String getType(){return this.type;}
	public String getValue(){return this.value;}
	public String getExtractValue(){return this.extractValue;}
	public int getAmount(){return this.amount;}
	public int getMetadata(){return this.metadata;}

}