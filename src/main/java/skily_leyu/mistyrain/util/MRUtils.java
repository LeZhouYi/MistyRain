package skily_leyu.mistyrain.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import skily_leyu.mistyrain.basic.Point3D;

/**
 * 一些用于Minecraft相关的方法
 * @author Skily
 * @version 1.0.0
 */
public class MRUtils{

	public static boolean isAirBlock(IBlockState blockState){
		return blockState.getBlock()==Blocks.AIR;
	}

	public static BlockPos toBlockPos(Point3D point){
		return new BlockPos(point.getX(),point.getY(),point.getZ());
	}
	
}