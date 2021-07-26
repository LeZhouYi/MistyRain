package skily_leyu.mistyrain.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import skily_leyu.mistyrain.basic.Dot;
import skily_leyu.mistyrain.basic.Trigger;
import skily_leyu.mistyrain.basic.MathUtils;

/**
 * 用于MR的说明书或成就
 * @author Skily
 * @version 1.0.0
 */
public class MRBookGenerator{

	/**创建时间，一般为首次打开该书的时间 */
	private long createTime;
	/**最后编辑时间，一般为最后一次数据更新的时间 */
	private long lastEditTime;
	/**拥有者，非拥者无法打开 */
	private String owner;
	/**存储所有收集点 */
	private Map<Integer,Dot> dots;
	/**存储所有节点 */
	private Map<Integer,Dot> sections;
	/**存储所有章节 */
	private Map<Integer,Dot> chapters;
	/**节点与收集点的对应Map，一个节点对应多个收集点 */
	private Map<Integer,List<Integer>> chapterMap;
	/**章节与节点的对应Map，一个章节对应多个节点 */
	private Map<Integer,List<Integer>> sectionMap;
	/**存储所有方块的触发器 */
	private Map<String,List<Trigger>> blockTriggers;
	/**存储所有物品的触发器 */
	private Map<String,List<Trigger>> itemTriggers;
	/**存储其它类型的触发器，如地形，天气，时令等 */
	private Map<String,List<Trigger>> otherTriggers;

	/**
	 * 将获得的成就添加进Map
	 * @param dotIndex 该成就点对应的index值
	 */
	public void addCollection(int dotIndex){
		initMap(false);
		if(this.dots.containsKey(dotIndex)){
			Dot dot = this.dots.get(dotIndex);
			addDotCollection(dot,true); //添加进对应的节点
			if(this.sections.containsKey(dot.getFather())){
				addSectionCollection(this.sections.get(dot.getFather()),false);//添加进对应的章节
			}
		}
	}

	/**
	 * 将当前节点添加进父节点中
	 * @param dot 指当前节点
	 * @param isDot 指是否是收集点，true=父节点为节点，false=父节点为章节
	 */
	private void addDotCollection(Dot dot, boolean isDot){
		Map<Integer,List<Integer>> map = (isDot)?(this.sectionMap):(this.chapterMap);
		if(this.sectionMap.containsKey(teDot.getFather())){
			//已存在该节点
			List<Integer> dotList = this.sectionMap.get(teDot.getFather());
			this.sectionMap.replace(teDot.getFather(),MathUtils.insert(dotList,value,true,true));
		}else{
			//不存在该节点
			List<Integer> dotList = new ArrayList<Integer>();
			dotList.add(value);
			this.sectionMap.put(teDot.getFather(),dotList);
		}
	}

	/**
	 * 初始化
	 * @param isRebuild true=直接新建，清空原有内容，fasle=仅当Map为空时新建
	 */
	public void initMap(boolean isRebuild){
		if(this.chapterMap == null||isRebuild){
			this.chapterMap = new HashMap<Integer,List<Integer>();
		}
		if(this.sectionMap == null||isRebuild){
			this.sectionMap = new HashMap<Integer,List<Integer>();
		}
	}

	/**解锁所有成就 */
	public void addAllCollections(){
		initMap(true);
		Set<Integer> dotKey = this.dots.keySet();//获取收集点集
		for(int value:dotKey){
			Dot teDot = dots.get(value);
			addDotCollection(teDot);
		}

		dotKey = this.sections.keySet();//获取节点集
		for(int value:dotKey){
			Dot teDot = sections.get(value);
			addSectionCollection(teDot);
	}

	//--------------------getter and setter-----------------------

	public Map<String,List<Trigger>> getOtherTriggers() {
		return otherTriggers;
	}

	public Map<String,List<Trigger>> getItemTriggers() {
		return itemTriggers;
	}


	public Map<String,List<Trigger>> getBlockTriggers() {
		return blockTriggers;
	}

	public Map<Integer,List<Integer>> getSectionMap() {
		return sectionMap;
	}

	public Map<Integer,Dot> getChapters() {
		return chapters;
	}

	public Map<Integer,Dot> getSections() {
		return sections;
	}

	public String getOwner() {
		return owner;
	}

	public long getLastEditTime() {
		return lastEditTime;
	}

	public long getCreateTime() {
		return createTime;
	}

}