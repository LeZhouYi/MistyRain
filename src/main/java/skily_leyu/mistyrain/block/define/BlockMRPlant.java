package skily_leyu.mistyrain.block.define;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skily_leyu.mistyrain.config.MRConfig;

/**
 * 具有生长特性的MR植物方法基类
 * @author Skily
 * @version 1.0.0
 */
public abstract class BlockMRPlant extends Block implements IMRPlant{

    public BlockMRPlant(Material material, MapColor mapColor){
        super(material,mapColor);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient){
        return false;
    }

    @Override
	public final int tickRate(World worldIn) {
		return MRConfig.tickSpeed;
	}

    @Override
	public final void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
        if(!worldIn.isRemote&&isAreaLoaded(worldIn,pos)){
            if(mustSupport()&&!hasSupport(worldIn, pos, state)){
                destroy(worldIn,rand,pos,state);
                return;
            }
            PlantEvent event = canGrow(worldIn, rand, pos, state);
            if(event==PlantEvent.GROW){
                grow(worldIn,rand,pos,state);
            }else if(event==PlantEvent.DECAY){
                decay(worldIn,rand,pos,state);
            }
        }
    }

    @Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state){
        return false;
    }

    @Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state){

    }
    @Override
    public void decay(World worldIn, Random rand, BlockPos pos, IBlockState state){

    }

    @Override
    public void destroy(World worldIn, Random rand, BlockPos pos, IBlockState state){

    }

    @Override
    public boolean hasSupport(World worldIn, BlockPos pos, IBlockState state){
        return true;
    }

    @Override
    public boolean isAreaLoaded(World worldIn, BlockPos pos){
        return worldIn.isAreaLoaded(pos,1);
    }

    @Override
    public boolean mustSupport(){
        return true;
    }

    @Override
    public PlantEvent canGrow(World worldIn, Random rand, BlockPos pos, IBlockState state){
        return PlantEvent.STOP;
    }

}
