package skily_leyu.mistyrain.basic.pattern;

/**
 * 二维坐标，整型
 * @author Skily
 * @version 1.0.0
 **/
public class Point2D{

	private int x;
	private int z;

	public Point2D(int size){
		this(size,size);
	}

	public Point2D(int x, int z){
		this.x = x;
		this.z = z;
	}

	/**
	 * 两点各分量差的平方和
	 * @param tePoint 二维点
	 * @return 各分量的差的平方和
	 **/
	public int squareDistance(Point2D tePoint){
		int x = this.x-tePoint.getX();
		int z = this.z-tePoint.getZ();
		return x*x+z*z;
	}

	/**
	 * @param length 各方向取点的长
	 * @return 各方向取点的集 
	 **/
	public Point2D[] getNearPoints(int length){
		return new Point2D[]{
			decrease(length,0),
			decrease(-length,0),
			decrease(0,length),
			decrease(0,-length)
		};
	}

	private Point2D decrease(int x, int z){
		return new Point2D(this.x-x,this.z-z);
	}

	public Point2D decrease(Point2D point){
		return decrease(point.getX(),point.getZ());
	}

	//---------- getter and setter-------------
	public int getX(){return x;}
	public int getZ(){return z;}
	public void setX(int x){this.x = x;}
	public void setZ(int z){this.z = z;}
}