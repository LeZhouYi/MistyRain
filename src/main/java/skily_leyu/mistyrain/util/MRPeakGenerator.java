package skily_leyu.mistyrain.util;

import java.util.Random;
import java.util.Stack;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import skily_leyu.mistyrain.basic.CellType;
import skily_leyu.mistyrain.basic.MathUtils;
import skily_leyu.mistyrain.basic.Pattern;
import skily_leyu.mistyrain.basic.Point2D;
import skily_leyu.mistyrain.basic.Point3D;

public class MRPeakGenerator extends WorldGenerator{
	
	//图格相关
	private int radius = 16; //基础图格半径
	private int size = 2*radius+1; //图集大小
	protected float[] spreadRate = new float[]{0.25F,0.35F,0.75F,0.85F,1.0F}; //图格比率
	protected float[] mixRate = new float[]{0.3F,0.6F}; //混合比率

	//Minecraft相关
	private Random rand; //生成器，通常为世界随机生成器
	private BlockPos pos; //中心点

	//Peak相关
	private int height; //高度
	private float gradient; //斜率
	private int bound = 16; //顶点随机范围[-16,16]

	public MRPeakGenerator(Random rand, BlockPos pos){
		this.rand = rand;
		this.pos = pos;
	}

	private IBlockState getCellBlock(CellType type, int height){
		return null;
	}

	private boolean canGenerate(){
		return false;
	}

	//生成基础图集
	private Pattern genBasePattern(){
		Pattern pattern = new Pattern(size);
		Point2D point = new Point2D(radius,radius);
		for(int x = 0; x < size; x++){
			for(int z = 0; z < size; z++){
				Point2D tePoint = new Point2D(x,z);
				pattern.setBit(tePoint,getCellType(point.squareDistance(tePoint)));
			}
		}
		Stack<Point2D> sideStack = pattern.getSideCell(CellType.SIDE,CellType.EMPTY);
		while(sideStack.size()>0){
			Point2D mainPoint = sideStack.pop();
			if(pattern.isEqual(mainPoint,CellType.SIDE)){
				for(Point2D nearPoint:mainPoint.getNearPoints(1)){
					if(pattern.isExist(nearPoint)){
						pattern.setBit(nearPoint, CellType.EMPTY);
					}
				}
				pattern.setBit(mainPoint,CellType.EMPTY);
			}
		}
		return pattern;
	}

	//图格绘制逻辑
	private CellType getCellType(int distance){
		float rate = (float)distance/(radius*radius);
		if(rate<=spreadRate[0]){
			return CellType.CORE;
		}else if(rate<=spreadRate[1]){
			return MathUtils.canDo(rand,mixRate[0])?CellType.MAIN:CellType.CORE;
		}else if(rate<=spreadRate[2]){
			return CellType.MAIN;
		}else if(rate<=spreadRate[3]){
			return MathUtils.canDo(rand,mixRate[1])?CellType.MAIN:CellType.SIDE;
		}else if(rate<=spreadRate[4]){
			return CellType.SIDE;
		}else{
			return CellType.EMPTY;
		}
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if(!canGenerate()){
			return false;
		}
		Pattern pattern = genBasePattern();
		Point3D base = new Point3D(pos.getX(),pos.getY(),pos.getZ());
		int xGap = MathUtils.randInt(rand,2*bound,-bound);
		int zGap = MathUtils.randInt(rand,2*bound,-bound);
		for(int h = 0, teSize = size;h<height;h++){
			float rate = (float)h/height;
			Point3D tePoint = new Point3D((int)(xGap*rate),h,(int)(zGap*rate));
			if(h!=0){
				teSize = (int)(size - gradient*rate);
				pattern = pattern.scalePattern(teSize,teSize);
			}
			Point2D center = new Point2D(teSize/2);
			for(int x = 0;x<teSize;x++){
				for(int z = 0;z<teSize;z++){
					BlockPos tePos = MRUtils.toBlockPos(base.add(tePoint).add(center.decrease(new Point2D(x,z))));
					IBlockState blockstate = getCellBlock(pattern.getBit(new Point2D(x,z)),h);
					if(!MRUtils.isAirBlock(blockstate)){
						worldIn.setBlockState(tePos,blockstate,2);
					}
				}
			}
		}
		return true;
	}

}