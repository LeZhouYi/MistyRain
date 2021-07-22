package skily_leyu.mistyrain.basic;
/**
 * 三维坐标，整型
 * @author Skily
 * @version 1.0.0
 **/

public class Point3D{

	private int x;
	private int y;
	private int z;

	public Point3D(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point3D add(Point3D point){
		return add(point.getX(),point.getY(),point.getZ());
	}

	private Point3D add(int x, int y, int z){
		return new Point3D(this.x+x,this.y+y,this.z+z);
	}

	public Point3D add(Point2D point){
		return add(point.getX(),0,point.getZ());
	}

	//---------------getter and setter -----------------
	public int getX(){return this.x;}
	public int getY(){return this.y;}
	public int getZ(){return this.z;}

}