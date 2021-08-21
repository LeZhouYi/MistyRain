package skily_leyu.mistyrain.utility;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.basic.MathUtils;
import skily_leyu.mistyrain.basic.pattern.Point3D;
import skily_leyu.mistyrain.basic.type.Season;
import skily_leyu.mistyrain.basic.type.SolarTerm;
import skily_leyu.mistyrain.block.define.IMRPlant;
import skily_leyu.mistyrain.config.MRConfig;

/**
 * 一些用于Minecraft相关的方法
 * @author Skily
 * @version 1.0.0
 */
public class MRUtils{

	private static List<Block> soilList = Lists.newArrayList(Blocks.GRASS, Blocks.DIRT, Blocks.FARMLAND);

	/**
	 * 返回下一个水平方向；若facing=Null，则随机一个水平方向
	 * @param facing 非空则以此为准返回上一个或下一个，若空若随机
	 * @param isForward true=返回上一个，false=返回下一个
	 * @return
	 */
	public static EnumFacing nextHorizontal(EnumFacing facing,boolean isForward){
		if(facing.getHorizontalIndex()<0){
			return EnumFacing.HORIZONTALS[0];
		}
		if(isForward){
			return EnumFacing.HORIZONTALS[(facing.getHorizontalIndex()+1)%4];
		}else{
			return EnumFacing.HORIZONTALS[(facing.getHorizontalIndex()+3)%4];
		}
	}

	/**
	 * 注册泥土，用于MR的植物对泥土的依赖判断
	 */
	public static void registerPlantSoil(Block... blocks){
		for(Block teBlock:blocks) {
			soilList.add(teBlock);
		}
	}

	/**
	 * 传递破坏消息，即继承了IMRPlant的方块可以使用该方法，用于植物依赖特性的连锁破坏
	 */
	public static void spreadDestroy(World worldIn, Random rand, BlockPos pos, IBlockState blockstate){
		if(isPlant(blockstate)){
			((IMRPlant)blockstate.getBlock()).checkSupport(worldIn, rand, pos, blockstate);
		}
	}

	/**
	 * 判断是为植物生长的泥士
	 * @param blockState
	 * @return
	 */
	public static boolean isPlantSoil(IBlockState blockState) {
		return soilList.contains(blockState.getBlock());
	}

	/**
	 * 判断是否是雪或雪相关方块
	 * @param blockstate
	 * @return
	 */
	public static boolean isSnow(IBlockState blockstate){
		return blockstate.getMaterial()==Material.SNOW;
	}

	/**
	 * @return 判断当前是否属于植物类型的方块
	 */
	public static boolean isPlant(IBlockState state){
		return state.getBlock().getClass().isInstance(IMRPlant.class);
	}

	/**
	 * 基础掉落随机
	 * @param rand
	 * @return
	 */
	public static boolean canDrop(Random rand){
		return MathUtils.canDo(rand,MRConfig.dropRate);
	}

	/**
	 * 基础生长随机
	 * @param rand
	 * @return
	 */
	public static boolean canGrow(Random rand){
		return MathUtils.canDo(rand,MRConfig.baseGrowRate);
	}

	/**
	 * 是否位于两个时令之间，包括两个时令
	 * @param start
	 * @param end
	 * @param value
	 * @return
	 */
	public static boolean isBetween(SolarTerm start, SolarTerm end, SolarTerm value){
		if(start.getStart()<=end.getStart()){
			return value.getStart()>=start.getStart()&&value.getStart()<=end.getStart();
		}else{
			return value.getStart()>=start.getStart()||value.getStart()<=end.getStart();
		}
	}

    /**
     * @return 获得当前的时令
     */
    public static SolarTerm getSolarTerm(World worldIn){
        float nowMonth = getNowMonth(worldIn);
        while(nowMonth>13.0F){
            nowMonth-=12.0F;
        }
        return SolarTerm.getSolarTerm(nowMonth);
    }

    /**
     * @return 获得当前的季节
     */
    public static Season getSeason(World worldIn){
        int indexMonth = ((int)getNowMonth(worldIn)-1)%12 + 1;
        if(indexMonth>=12||indexMonth<=2){
            return Season.WINTER;
        }else{
            return Season.values()[indexMonth/3-1];
        }
    }

    /**
     *@return 获得当前日期的浮点值
     */
    public static float getNowMonth(World worldIn){
        long days = (worldIn.getWorldTime() / 24000);
        return MRConfig.monthStart+(float)days/MRConfig.monthDays;
    }

	/**
	 * 调试，输入变量名及取值
	 */
	public static void logInfo(String name, Object object) {
		MistyRain.getLogger().info(name+":"+object.toString());
	}
	
	/**
	 * 判断是否为覆盖类型的方块
	 * @return true=是覆盖类型的方块
	 */
	public static boolean isCoverBlock(IBlockState blockstate){
		return blockstate.isFullCube()&&!blockstate.isOpaqueCube();
	}

	/**
	 * 判断该方块是否为Air
	 * @param blockState 需要判断的方法，无检查
	 * @return true=是空气方块
	 */
	public static boolean isAirBlock(IBlockState blockState){
		return blockState.getBlock()==Blocks.AIR;
	}

	/**
	 * 将Point3d转化BlockPos
	 * @param point 无检查
	 * @return BlockPos
	 */
	public static BlockPos toBlockPos(Point3D point){
		return new BlockPos(point.getX(),point.getY(),point.getZ());
	}
	
	/**
	 * 判断当前方块是否可以被替换的，可能会更改判定条件，一般用于世界生成
	 * @param worldIn 方块所在的维度
	 * @param pos 方块所在的位置
	 * @return true=可替换
	 */
	public static boolean canReplace(World worldIn, BlockPos pos){
		IBlockState blockstate = worldIn.getBlockState(pos);
		if(blockstate.getBlock()==Blocks.AIR||blockstate.getMaterial() == Material.WATER){
			return true;
		}
		return false;
	}

}