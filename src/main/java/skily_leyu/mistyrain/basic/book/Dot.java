package skily_leyu.mistyrain.basic.book;

/**
 * 收集点的存储结构
 * @author Skily
 * @version 1.0.0
 */
public class Dot{

	/**描述，用于标题 */
	private String describe;
	/**图片路径，用于图标或内容 */
	private String image;
	/**内容，一般为提示文本或详细内容 */
	private String content;
	/**父节点，指示上一级目录在该层级的顺序 */
	private int father;


	//--------------------getter and setter---------------------
	public String getDescribe(){return this.describe;}
	public String getImage(){return this.image;}
	public String getContent(){return this.content;}
	public int getFather(){return this.father;}

}