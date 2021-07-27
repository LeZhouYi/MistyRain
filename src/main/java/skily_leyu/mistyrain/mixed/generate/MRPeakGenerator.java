package skily_leyu.mistyrain.mixed.generate;

import java.util.List;
import java.util.Random;
import java.util.Stack;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import skily_leyu.mistyrain.basic.MathUtils;
import skily_leyu.mistyrain.basic.pattern.Pattern;
import skily_leyu.mistyrain.basic.pattern.Point2D;
import skily_leyu.mistyrain.basic.pattern.Point3D;
import skily_leyu.mistyrain.basic.type.CellType;
import skily_leyu.mistyrain.mixed.MRUtils;

public class MRPeakGenerator extends WorldGenerator{
	
	//图格相关
	private int radius = 16; //基础图格半径
	private int size = 2*radius+1; //图集大小
	protected float[] spreadRate = new float[]{0.25F,0.35F,0.75F,0.85F,1.0F}; //图格比率
	protected float[] mixRate = new float[]{0.3F,0.6F}; //混合比率

	//Minecraft相关
	private Random rand; //生成器，通常为世界随机生成器
	private BlockPos pos; //中心点
	private List<IBlockState> blockList;//图格对应的方块列表,以CellType.ordinal()的取值对应

	//Peak相关
	private int height = 100; //高度
	private int depth = 4; //填充深度
	private float gradient = 0.1F; //斜率
	private int bound = 16; //顶点随机范围[-16,16]

	public MRPeakGenerator(List<IBlockState> blockList){
		this.blockList = blockList;
	}

	/**
	 * 初始化必要的参数
	 */
	private void init(Random rand, BlockPos pos){
		this.rand = rand;
		this.pos = pos;
	}

	/**
	 * 根据图格取值和当前高度，返回对应的方块
	 * @param type 图格取值
	 * @param height 当前处理生成的相对高度
	 * @return 返回对应逻辑的方块状态
	 */
	private IBlockState getCellBlock(CellType type, int height){
		if(height<this.height-1){
			return this.blockList.get(type.ordinal());
		}else{
			return this.blockList.get(CellType.SURFACE.ordinal());
		}
	}

	/**
	 * 检查当前是否可以生成
	 * @param worldIn 自建维度
	 * @param position 生成的中心点，检查的中心点
	 * @return true=允许生成，false=不允许生成
	 */
	private boolean canGenerate(World worldIn, BlockPos position){
		//检查区块加载
		if(!worldIn.isAreaLoaded(position, radius)){
			return false;
		}
		//检查是否存在支撑方块
		int checkRadius = (int)(this.radius*0.75F);
		for(BlockPos tePos:BlockPos.getAllInBox(position.add(-checkRadius,-1,-checkRadius),position.add(checkRadius,-1,checkRadius))){
			if(!MRUtils.isAirBlock(worldIn.getBlockState(tePos))){
				return false;
			}
		}
		return true;
	}

	/**
	 * 生成基础图集,一般用于最初位置该层的生成
	 * @return 返回根据设置生成的最初的图集
	 */
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

	/**
	 * 图格绘制逻辑，根据距离返回不同的取值，有依赖属性配置
	 * @param distance 一般为中心点与该点的轴差平方和，与半径的平方作差
	 * @return 返回对应逻辑的取值
	 */
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

	/**
	 * 生成主入口，生成成功则返回True
	 */
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		//检查是否可以生成
		if(!canGenerate(worldIn,position)){
			return false;
		}
		//初始化参数
		init(rand,position);

		//生成基础图层数据
		Pattern pattern = genBasePattern();
		Point3D base = new Point3D(pos.getX(),pos.getY(),pos.getZ());

		//生成顶层中心点
		int xGap = MathUtils.randInt(rand,2*bound,-bound);
		int zGap = MathUtils.randInt(rand,2*bound,-bound);

		for(int h = -this.depth, teSize = size;h<height;h++){
			//线性插值
			float rate = (float)h/height;
			Point3D tePoint = new Point3D((int)(xGap*rate),h,(int)(zGap*rate));//偏移量

			//第0层不需要放缩
			if(h!=0){
				teSize = (int)(size - gradient*rate);
				pattern = pattern.scalePattern(teSize,teSize);
			}
			//获取中心
			Point2D center = new Point2D(teSize/2);

			for(int x = 0;x<teSize;x++){
				for(int z = 0;z<teSize;z++){
					//BlockPos=中心+层级偏移量+图格级偏移量
					BlockPos tePos = MRUtils.toBlockPos(base.add(tePoint).add(center.decrease(new Point2D(x,z))));
					IBlockState blockstate = getCellBlock(pattern.getBit(new Point2D(x,z)),h);
					if(!MRUtils.isAirBlock(blockstate)&&MRUtils.canReplace(worldIn,tePos)){
						worldIn.setBlockState(tePos,blockstate,2);
					}
				}
			}
		}
		return true;
	}

	//---------------getter and setter----------------
	public MRPeakGenerator setRadius(int radius){
		this.radius = radius;
		this.size = 2*radius+1;
		return this;
	}

	public MRPeakGenerator setSpreadRate(float[] spreadRate){
		this.spreadRate = spreadRate;
		return this;
	}

	public MRPeakGenerator setMixRate(float[] mixRate){
		this.mixRate = mixRate;
		return this;
	}

	public MRPeakGenerator setHeight(int height){
		this.height=height;
		return this;
	}

	public MRPeakGenerator setGradient(float gradient){
		this.gradient = gradient;
		return this;
	}

	public MRPeakGenerator setBound(int bound){
		this.bound = bound;
		return this;
	}

	public MRPeakGenerator setDepth(int depth){
		this.depth = depth;
		return this;
	}

}