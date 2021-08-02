package skily_leyu.mistyrain.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import skily_leyu.mistyrain.block.MRProperty;

/**
 * 雨石
 * @author Skily
 * @version 1.0.0
 */
public class BlockRainStone extends Block{

    /**
     * 0 = 普通的石头状态
     * 1 = 有草状态
     * 2 = 上方有雪时，有雪状态
     * 3 = 顶上无草，侧生草状态
     * 4 = 顶上有草，侧生草状态
     * 5 = 顶上有雪，侧生草状态
     */
    public static final IProperty<Integer> STAGE = MRProperty.HALF_STAGE;

    public BlockRainStone(){
        super(Material.ROCK,MapColor.BLUE_STAINED_HARDENED_CLAY);
        this.setHardness(MRProperty.stoneHardness);
        this.setResistance(MRProperty.stoneHardness);
        this.setSoundType(SoundType.STONE);
        this.setDefaultState(blockState.getBaseState().withProperty(STAGE,0));
        this.setTickRandomly(true);
    }

    @Override
    public int getMetaFromState(IBlockState blockstate){
        return blockstate.getValue(STAGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(STAGE,meta%4);
    }

    @Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { STAGE });
	}

    @Override
	public int tickRate(World worldIn) {
		return MRConfig.tickSpeed;
	}

    @Override
    public boolean isFullCube(IBlockState state){
        return true;
    }
    
    @Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super(worldIn,pos,state,rand);
        if(!worldIn.isRemote&&worldIn.isAreaLoaded(pos, 1)){
            boolean hasCover = MRUtils.isCoverBlock(worldIn.getBlockState(pos.up()));
            for(EnumFacing facing:EnumFacing.plane.HORIZONTAL){
            }
        }
    }

}