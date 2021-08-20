package skily_leyu.mistyrain.block.plant.flowingcyan;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skily_leyu.mistyrain.basic.type.SolarTerm;
import skily_leyu.mistyrain.block.MRBlocks;
import skily_leyu.mistyrain.block.MRProperty;
import skily_leyu.mistyrain.block.define.BlockMRPlant;
import skily_leyu.mistyrain.block.define.PlantEvent;
import skily_leyu.mistyrain.utility.MRUtils;

public class BlockFlowingCyanSapling extends BlockMRPlant{

    public static final IProperty<EnumFacing> FACING = MRProperty.FACING;
    public static final IProperty<Boolean> VERTICAL = MRProperty.IS_VERTICAL;
    public static final IProperty<Integer> STAGE = MRProperty.TWO_STAGE;

    public BlockFlowingCyanSapling(){
        super(Material.PLANTS, MapColor.GREEN);
        this.setHardness(MRProperty.leavesHardness);
        this.setResistance(MRProperty.leavesHardness);
        this.setSoundType(SoundType.PLANT);
        this.setDefaultState(blockState.getBaseState().withProperty(STAGE,0).withProperty(FACING,EnumFacing.EAST).withProperty(VERTICAL,false));
        this.setTickRandomly(true);
    }

    @Override
    public int getMetaFromState(IBlockState blockstate){
        return blockstate.getValue(FACING).getHorizontalIndex()*4 
            + (blockstate.getValue(VERTICAL)?1:0)*2 + (blockstate.getValue(STAGE));
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING,EnumFacing.HORIZONTALS[meta/4])
            .withProperty(VERTICAL,Boolean.valueOf(meta%4/2==1)).withProperty(STAGE,meta%2);
    }

    @Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VERTICAL,FACING });
	}

    //-------------------Client Method or Property-------------------------------

    //-------------------Block Method --------------------------------
    
    /**
     * 监测依赖方块的变化
     */
    @Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        boolean flag = false;
        if(state.getValue(VERTICAL)){
            flag = fromPos==pos.down();
        }else{
            flag = fromPos==pos.offset(state.getValue(FACING));
        }
        if(flag){
            checkSupport(worldIn,worldIn.rand,pos,state);
        }
    }
    
    //-------------------Plant Event and Method-----------------------------
    @Override
    public boolean hasSupport(World worldIn, BlockPos pos, IBlockState state){
        if(state.getValue(VERTICAL)){
            return isSuitBlock(worldIn.getBlockState(pos.down()));
        }else{
            return isSuitBlock(worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())));
        }
    }

    @Override
    public boolean isSuitBlock(IBlockState blockstate){
        return MRUtils.isPlantSoil(blockstate)||blockstate.getBlock()==MRBlocks.rainStone;
    }

    @Override
    public void destroy(World worldIn, Random rand, BlockPos pos, IBlockState state){
        super.destroy(worldIn,rand,pos,state);
        //掉落
        if(MRUtils.canDrop(rand)){
            spawnAsEntity(worldIn, pos, new ItemStack(MRBlocks.flowingCyanLog));
        }
    }

    @Override
    public PlantEvent canGrow(World worldIn, Random rand, BlockPos pos, IBlockState state){
        if(worldIn.isRaining()){
            SolarTerm solarTerm = MRUtils.getSolarTerm(worldIn);
            if(MRUtils.isBetween(SolarTerm.BeginningOfSpring,SolarTerm.GrainRain,solarTerm)){
                return MRUtils.canGrow(rand)?PlantEvent.GROW:PlantEvent.STOP;
            }else if(MRUtils.isBetween(SolarTerm.FirstFrost,SolarTerm.GreaterCold,solarTerm)){
                return MRUtils.canGrow(rand)?PlantEvent.DECAY:PlantEvent.STOP;
            }
        }
        return PlantEvent.STOP;
    }

    @Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state){
        if(state.getValue(STAGE)==0){
            worldIn.setBlockState(pos,state.withProperty(STAGE,1),2);
        }else{
            //TODO: grow flowing cyan tree
        }
    }
    @Override
    public void decay(World worldIn, Random rand, BlockPos pos, IBlockState state){
        destroy(worldIn,rand,pos,state);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient){
        return true;
    }

}
