package skily_leyu.mistyrain.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import skily_leyu.mistyrain.basic.Dot;
import skily_leyu.mistyrain.basic.Trigger;
import skily_leyu.mistyrain.basic.MathUtils;

public class MRBookGenerator{

	private long createTime;
	private long lastEditTime;
	private String owner;
	private Map<Integer,Dot> dots;
	private Map<Integer,Dot> sections;
	private Map<Integer,Dot> chapters;
	private Map<Integer,List<Integer>> chapterMap;
	private Map<Integer,List<Integer>> sectionMap;
	private Map<String,List<Trigger>> blockTriggers;
	private Map<String,List<Trigger>> itemTriggers;
	private Map<String,List<Trigger>> otherTriggers;

	public void addAllCollections(){
		this.chapterMap = new HashMap<Integer,List<Integer>>();
		this.sectionMap = new HashMap<Integer,List<Integer>>();
		Set<Integer> dotKey = dots.keySet();
		for(int value:dotKey){
			Dot teDot = dots.get(value);
			if(chapterMap.containsKey(teDot.getFather())){
				List<Integer> dotList = new ArrayList<Integer>();
				dotList.add(value);
				this.chapterMap.put(teDot.getFather(),dotList);
			}else{
				List<Integer> dotList = this.chapterMap.get(teDot.getFather());
				this.chapterMap.replace(teDot.getFather(),MathUtils.insert(dotList,value,true,true));
			}
		}
		// dotKey = sections.keySet();
		// for(int value:dotKey){
		// 	Dot teDot = sections.get(value);
			
		// }
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