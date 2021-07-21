package skily_leyu.mistyrain.basic;

import java.util.Stack;

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
	public boolean isExsit(Point2D point){
		return MathUtils.isBetween(0,x,point.getX()-1)&&MathUtils.isBetween(0,z,point.getZ());
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
						if(isExsit(tePoint)&&isEqual(tePoint,near)){
							points.push(point);
							break;
						}
					}
				}
			}
		}
		return points;
	}

	/**
	 * 裁剪对应位置框定的图集，返回新的矩阵
	 * @param start 起点
	 * @param end 终点
	 * @return 返回截取的新的矩阵
	 **/
	public Pattern extractPattern(Point2D start, Point2D end){
		if(!isExsit(start)||!isExsit(end)){
			return null;
		}
		int startX = MathUtils.min(start.getX(),end.getX());
		int endX = MathUtils.max(start.getX(),end.getX());
		int startZ = MathUtils.min(start.getZ(),end.getZ());
		int endZ = MathUtils.max(start.getZ(),end.getZ());
		int sizeX = endX-startX+1;
		int sizeZ = endZ-startZ+1;
		Pattern pattern = new Pattern(sizeX,sizeZ);
		for(int x = 0;x<sizeX;x++){
			for(int z = 0;z<sizeZ;z++){
				Point2D point = new Point2D(startX+x,startZ+z);
				pattern.setBit(new Point2D(x,z),this.getBit(point));
			}
		}
		return pattern;
	}

	/**
	 * 将原矩阵放缩为给出尺寸的矩阵
	 * @param sizeX 放缩后的行
	 * @param sizeZ 放缩后的列
	 * @return 放缩后的矩阵
	 **/
	public Pattern scalePattern(int sizeX, int sizeZ){
		Pattern pattern = new Pattern(sizeX,sizeZ);
		float stepX = (this.x-1.0F)/sizeX;
		float stepZ = (this.z-1.0F)/sizeZ;
		Point2D bePoint = new Point2D(0,0);
		for(int x = 0;x<sizeX;x++){
			int pointX = (int)((x+1)*stepX);
			for(int z = 0;z<sizeZ;z++){
				int pointZ = (int)((z+1)*stepZ);
				Point2D afPoint = new Point2D(pointX,pointZ);
				Pattern exPattern = extractPattern(bePoint,afPoint);
				pattern.setBit(new Point2D(x, z),exPattern.maxFrequency());
				bePoint = afPoint;
			}
		}
		return pattern;
	}

	/**
	 *@return 出现频率最大的值
	 **/
	public CellType maxFrequency(){
		int[] count = MathUtils.genIntArray(CellType.values().length,0);
		for(int x = 0;x<this.x;x++){
			for(int z =0;z<this.z;z++){
				CellType teType = this.getBit(new Point2D(x,z));
				count[teType.ordinal()]+=1;
			}
		}
		return CellType.values()[MathUtils.maxIndex(count)];
	}

}