package skily_leyu.mistyrain.mixed;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skily_leyu.mistyrain.basic.pattern.Point3D;

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
	
	public static boolean canReplace(World worldIn, BlockPos pos){
		IBlockState blockstate = worldIn.getBlockState(pos);
		if(blockstate.getBlock()==Blocks.AIR||blockstate.getMaterial() == Material.WATER){
			return true;
		}
		return false;
	}

}