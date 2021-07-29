package skily_leyu.mistyrain.block.define;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import skily_leyu.mistyrain.block.define.IMRPlant;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.block.define.PlantEvent;

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
	boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient){
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
            if(mustSupport()&&!hasSupport()){
                destroy(world,rand,pos,state);
                return;
            }
            PlantEvent event = canGrow(worldIn, rand, pos, state);
            if(event==PlantEvent.GROW){
                grow(world,rand,pos,state);
            }else if(event==PlantEvent.DECAY){
                decay(world,rand,pos,state);
            }
        }
    }

    @Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state){
        updateTick(worldIn,pos,state,rand);
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
