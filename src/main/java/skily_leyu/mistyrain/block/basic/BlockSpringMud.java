package skily_leyu.mistyrain.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import skily_leyu.mistyrain.block.MRProperty;
import skily_leyu.mistyrain.block.define.BlockMRPlant;

/**
 * 春泥
 * @author Skily
 * @version 1.0.0
 */
public class BlockSpringMud extends Block{
    /**
     * state = 0, 普通的泥土状态
     * stage = 1, 当上方无完整方法, 冬天，无雪，草雪混合状态
     * stage = 2，当上方无完整方法，非冬天，有草状态
     * stage = 3，当上方无完整方块，春夏天，有花状态
     * stage = 4, 当上方为有雪，且为冬天，有雪状态
     */
    public static final IProperty<Integer> STAGE = MRProperty.HALF_STAGE;

    public BlockSpringMud(){
        super(Material.GROUND,MapColor.GREEN_STAINED_HARDENED_CLAY);
        this.setHardness(MRProperty.dirtHardness);
        this.setResistance(MRProperty.dirtHardness);
        this.setSoundType(SoundType.GROUND);
        this.setDefaultState(blockState.getBaseState().withProperty(STAGE,0));
        this.setTickRandomly(true);
    }

    @Override
    public int getMetaFromState(IBlockState blockstate){
        return blockstate.getValue(STAGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(STAGE,meta);
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
    public int isFullCube(IBlockState state){
        return true;
    }

    @Override
	public final void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super(worldIn,pos,state,rand);
        if(!worldIn.isRemote&&worldIn.isAreaLoaded(pos,1)){
            if(MRUtils.isCoverBlock(worldIn.getBlockState(pos.up()))){
                //有覆盖方块
                worldIn.setBlockState(pos,state.withProperty(STAGE,0),2);
                return;
            }
            Season season = MRUtils.getSeason(worldIn);
            if(season==Season.WINTER){
                if(worldIn.getBlockState(pos.up).getMaterial()==Material.SNOW){
                    //冬天有雪
                    worldIn.setBlockState(pos,state.withProperty(STAGE,4),2);
                }else{
                    //冬天无雪
                    worldIn.setBlockState(pos,state.withProperty(STAGE,1),2)
                }
            }else if(season==Season.AUTUMN){
                //非冬，非春夏，仅草
                worldIn.setBlockState(pos,state.withProperty(STAGE,1),2)
            }else{
                int stage = state.getValue(STAGE);
                if(stage!=2&&stage!=3){
                    //长草
                    worldIn.setBlockState(pos,state.withProperty(STAGE,1),2)
                }else if(stage==2){
                    //长花
                    if(MRUtils.canGrow(rand)){
                        worldIn.setBlockState(pos,state.withProperty(STAGE,3),2)
                    }
                }
            }
        }
    }

}
