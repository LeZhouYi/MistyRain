package skily_leyu.mistyrain.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 一些常用的或者可以通用的辅助函数
 * @author Skily
 * @version 1.0.0
 **/
public class MathUtils{
	
	/**
	 * 控制概率的精度=0.001
	 **/
	public static final int BOUND = 1000;


	/**
	 * 将数据插入有序列表中
	 * @param list 有序列表
	 * @param value 插入的值
	 * @param isDesc 是否降序
	 * @param isUnique 是否唯一，重复即不插入
	 * @return 返回新的有序列表
	 **/
	public List<Integer> insert(List<Integer> list, int value ,boolean isDesc, boolean isUnique){
		List<Integer> telist = new ArrayList<Integer>();
		telist.addAll(list);
		for(int i = 0;i<telist.size();i++){
			if(isDesc){
				if(value<telist.get(i)){
					telist.add(i,value);
					return telist;
				}else if(value==telist.get(i)){
					if(!isUnique){
						telist.add(i,value);
					}
					return telist;
				}
			}else{
				if(value>telist.get(i)){
					telist.add(i,value);
					return telist;
				}else if(value==telist.get(i)){
					if(!isUnique){
						telist.add(i,value);
					}
					return telist;
				}
			}
		}
		telist.add(value);
		return telist;
	}

	/**
	 * @return 返回最大值的下标
	 **/
	public static int maxIndex(int[] array){
		int index = 0;
		for(int i = 1;i<array.length;i++){
			if(array[index]<array[i]){
				index = i;
			}
		}
		return index;
	}

	/**
	 * @param length 要生成的数组的长度
	 * @param fill 要填充的数
	 * @return 返回生成的整型数组
	 **/
	public static int[] genIntArray(int length, int fill){
		int[] array = new int[length];
		for(int i = 0;i<length;i++){
			array[i] = fill;
		}
		return array;
	}

	/**
	 * @param rand 随机数生成器
	 * @param bound 随机范围[base,base+bound]
	 * @param base 最小值
	 * @return 返回随机整数
	 **/
	public static int randInt(Random rand, int bound, int base){
		return base + rand.nextInt(bound);
	}

	/**
	 * @param rand 随机数生成器
	 * @param bound 随机范围[0,bound]
	 * @return 返回随机整数
	 **/
	public static int randInt(Random rand, int bound){
		return rand.nextInt(bound);
	}

	/**
	 * 是否执行
	 * @param rand 随机数生成器
	 * @param rate 概率，约束精度为0.001
	 **/
	public static boolean canDo(Random rand, float rate){
		return rand.nextInt(BOUND)<BOUND*rate;
	}

	/**
	 * 最小值
	 **/
	public static int min(int x, int y){
		return x>y?y:x;
	}

	/**
	 * 最大值
	 **/
	public static int max(int x, int y){
		return x>y?x:y;
	}

	/**
	 * 是否范围内
	 **/
	public static boolean isBetween(int start, int end, int value){
		return start<=value&&value<=end;
	}

	/**
	 * @param arrays 求和列表
	 * @return 列表各值的平方之和
	 **/
	public static int sumOfSquare(int... arrays){
		int sum = 0;
		for(int value:arrays){
			sum+=(value*value);
		}
		return sum;
	}

}