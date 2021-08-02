package skily_leyu.mistyrain.basic.pattern;

import java.util.Stack;

import skily_leyu.mistyrain.basic.MathUtils;
import skily_leyu.mistyrain.basic.type.CellType;

/**
 * 二维矩阵，取值为CellType
 * @author Skily
 * @version 1.0.0
 **/
public class Pattern{

	/** 矩阵的行 **/
	private int x;
	/** 矩阵的列 **/ 
	private int z;
	/** 矩阵存取二维数组 **/
	private CellType[][] matrix;

	public Pattern(int sizeX, int sizeZ){
		this.x = sizeX;
		this.z = sizeZ;
		this.matrix = new CellType[this.x][this.z];
	}

	public Pattern(int size){
		this(size,size);
	}

	/** 
	 * 设置特定图格的值，无边界检查
	 * @param point 代表矩阵上某点
	 * @param type 图格的值
	 **/
	public void setBit(Point2D point, CellType type){
		this.matrix[point.getX()][point.getZ()]=type;
	}

	/**
	 * 获取特定图格的值，无边界检查
	 * @param point 代表矩阵上某点
	 * @return 对应图格的值
	 **/
	public CellType getBit(Point2D point){
		return this.matrix[point.getX()][point.getZ()];
	}

	/**
	 * 检查特定位置图格的值是否与给出的值相等
	 * @param point 代表矩阵上某点
	 * @param type 用于比较的值
	 * @return true=相等，false=不相等
	 **/
	public boolean isEqual(Point2D point, CellType type){
		return this.matrix[point.getX()][point.getZ()]==type;
	}
	
	/**
	 * 边界检查，检查该位置是否位于矩阵之中
	 * @param point 待检查的点
	 * @return true=矩阵之内，flase=矩阵之外
	 **/
	public boolean isExist(Point2D point){
		return MathUtils.isBetween(0,x-1,point.getX())&&MathUtils.isBetween(0,z-1,point.getZ());
	}

	/**
	 * 若某点的值为main,且十字方向附近存在值为near，则记录该点的位置存入栈中并返回
	 * @param main 主要检索引
	 * @param near 邻近是否存在该值
	 * @return 符合条件的位置的栈
	 **/
	public Stack<Point2D> getSideCell(CellType main,CellType near){
		Stack<Point2D> points = new Stack<Point2D>();
		for(int x = 0;x<this.x;x++){
			for(int z = 0;z<this.z;z++){
				Point2D point = new Point2D(x,z);
				if(isEqual(point, main)){
					for(Point2D tePoint:point.getNearPoints(1)){
						if(isExist(tePoint)&&isEqual(tePoint,near)){
							points.push(point);
							break;
						}
					}
				}
			}
		}
		return points;
	}

}