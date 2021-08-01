package skily_leyu.mistyrain.mixed;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.basic.pattern.Point3D;

/**
 * 一些用于Minecraft相关的方法
 * @author Skily
 * @version 1.0.0
 */
public class MRUtils{

	public static void logInfo(String name, Object object) {
		MistyRain.getLogger().info(name+":"+object.toString());
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