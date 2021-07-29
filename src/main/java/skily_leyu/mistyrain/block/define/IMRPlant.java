package skily_leyu.mistyrain.block.define;

import net.minecraft.block.IGrowable;

/**
 * 使用原版生长的一些特性基础上，增加一些自身方便进行代码组织的方法
 * 仅为接口，具体的逻辑实现在{@link BlockMRPlant}
 * @author Skily
 * @version 1.0.0
 */
public interface IMRPlant extends IGrowable{

	/**
	 * 是否出现骨粉特效，同时消耗骨粉
     * @return true=出现并消耗，false=不出现且不消耗
	 */
	@Override
	boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient);

	/**
	 * 是否在消耗骨粉后，能否进入生长
     * @return true=进入生长，false=不进入生长
	 */
	@Override
	boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state);

	/**
	 * 生长方法
	 */
	@Override
	void grow(World worldIn, Random rand, BlockPos pos, IBlockState state);

	/**
	 * 枯萎方法
	 */
	void decay(World worldIn, Random rand, BlockPos pos, IBlockState state);

	/**
	 * 破坏方法，用于处理方块的消失
	 */
	void destroy(World worldIn, Random rand, BlockPos pos, IBlockState state);

	/**
	 * 检查是否具有依赖
	 * @return true=具有依赖
	 */
	boolean hasSupport(World worldIn, BlockPos pos, IBlockState state);

	/**
	 * 是否必须检索依赖
	 * @return true=强制性，false=不强制，即不检索
	 */
	boolean mustSupport();

	/**
	 * 根据状态返回此时应执行的方法
	 * @return GrowType 返回对应的状态
	 */
	GrowType canGrow(World worldIn, Random rand, BlockPos pos, IBlockState state);

}