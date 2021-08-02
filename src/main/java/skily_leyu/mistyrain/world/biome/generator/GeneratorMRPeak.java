package skily_leyu.mistyrain.world.biome.generator;

import java.util.List;
import java.util.Random;
import java.util.Stack;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.basic.MathUtils;
import skily_leyu.mistyrain.basic.pattern.Pattern;
import skily_leyu.mistyrain.basic.pattern.Point2D;
import skily_leyu.mistyrain.basic.pattern.Point3D;
import skily_leyu.mistyrain.basic.type.CellType;

public class GeneratorMRPeak extends WorldGenerator{
	
	//图格相关
	private int radius; //基础图格半径
	private int size; //图集大小
	protected float[] spreadRate; //图格比率
	protected float[] mixRate; //混合比率

	//Minecraft相关
	private Random rand; //生成器，通常为世界随机生成器
	private BlockPos pos; //中心点
	private List<IBlockState> blockList;//图格对应的方块列表,以CellType.ordinal()的取值对应

	//Peak相关
	private int height; //高度
	private int depth; //填充深度
	private float gradient; //斜率
	private int bound; //顶点随机范围
	private float checkRate;//检查范围

	public GeneratorMRPeak(List<IBlockState> blockList){
		this.blockList = blockList;
	}

	/**
	 * 初始化必要的参数
	 */
	private void init(Random rand, BlockPos pos){
		this.rand = rand;
		this.pos = pos;
		if(this.radius==0){
			this.setRadius(MRConfig.cloudPeakRadius);
		}
		if(this.spreadRate==null){
			this.setSpreadRate(MRConfig.cloudPeakCellRate);
		}
		if(this.mixRate==null){
			this.setMixRate(MRConfig.cloudPeakMixRate);
		}
		if(this.depth==0){
			this.setDepth(MRConfig.cloudPeakDepth);
		}
		if(this.bound==0){
			this.setBound(MRConfig.cloudPeakTopBound);
		}
		if(this.height==0){
			this.setHeight(MRConfig.cloudPeakMinHeight+this.rand.nextInt(MRConfig.cloudPeakMaxHeight-MRConfig.cloudPeakMinHeight));
		}
		if(this.gradient==0.0F){
			this.setGradient(MRConfig.cloudPeakMinGradient+this.rand.nextFloat(MRConfig.cloudPeakMaxGradient-MRConfig.cloudPeakMinGradient));
		}
		if(this.checkRate==0.0F){
			this.checkRate=MRConfig.cloudPeakCheckRate;
		}
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
		if(!worldIn.isAreaLoaded(position, radius+1)){
			return false;
		}
		//检查上限及下限
		if(position.getY()<=depth||position.getY()+height>=world.getHeight()){
			return false;
		}
		//检查是否存在支撑方块
		int checkRadius = (int)(this.radius*this.checkRate);
		for(BlockPos tePos:BlockPos.getAllInBox(position.add(-checkRadius,-1,-checkRadius),position.add(checkRadius,-1,checkRadius))){
			if(MRUtils.isAirBlock(worldIn.getBlockState(tePos))){
				return false;
			}
		}
		return true;
	}

	/**
	 * 生成基础图集
	 * @param size 图集大小
	 * @param radius 图集半径
	 * @return 返回根据设置生成图集
	 */
	private Pattern genBasePattern(int size,int radius){
		Pattern pattern = new Pattern(size);
		Point2D point = new Point2D(radius,radius);
		for(int x = 0; x < size; x++){
			for(int z = 0; z < size; z++){
				Point2D tePoint = new Point2D(x,z);
				pattern.setBit(tePoint,getCellType(point.squareDistance(tePoint,radius)));
			}
		}
		Stack<Point2D> sideStack = pattern.getSideCell(CellType.SIDE,CellType.EMPTY);
		while(sideStack.size()>0){
			Point2D mainPoint = sideStack.pop();
			if(pattern.isEqual(mainPoint,CellType.SIDE)){
				for(Point2D nearPoint:mainPoint.getNearPoints(1)){
					if(pattern.isExist(nearPoint)){
						pattern.setBit(nearPoint, CellType.EMPTY);
						//TODO:修改边缘
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
	 * @param radius 当前图集的半径
	 * @return 返回对应逻辑的取值
	 */
	private CellType getCellType(int distance, int radius){
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

		//初始化参数
		init(rand,position);

		//生成顶层中心点
		int xGap = MathUtils.randInt(rand,2*bound,-bound);
		int zGap = MathUtils.randInt(rand,2*bound,-bound);

		//检查是否可以生成
		if(!canGenerate(worldIn,position)||!worldIn.isAreaLoaded(position.add(xGap,height,zGap),(int)((size-gradient)/2))){
			return false;
		}

		Point3D base = new Point3D(pos.getX(),pos.getY(),pos.getZ());
		for(int h = -this.depth, teSize = size;h<height;h++){
			//线性插值
			float rate = (float)h/height;
			Point3D tePoint = new Point3D((int)(xGap*rate),h,(int)(zGap*rate));//偏移量

			if(size!=(int)(size - gradient*rate)&&h!=-this.depth){
				teSize = (int)(size - gradient*rate);
				pattern = genBasePattern(teSize, teSize/2)
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