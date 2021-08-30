package skily_leyu.mistyrain.block.basic;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skily_leyu.mistyrain.block.MRProperty;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.item.MRItems;
import skily_leyu.mistyrain.utility.MRUtils;

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

    //---------------------Block Property or Method-----------------------

    @Override
	public int tickRate(World worldIn) {
		return MRConfig.tickSpeed;
	}

    @Override
    public boolean isFullCube(IBlockState state){
        return true;
    }

    //----------------------Update or Event Method--------------------------

    @Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if(!worldIn.isRemote&&worldIn.isAreaLoaded(pos, 1)){
            boolean hasCover = MRUtils.isCoverBlock(worldIn.getBlockState(pos.up()));
            boolean hasSnow = MRUtils.isSnow(worldIn.getBlockState(pos.up()));
            boolean hasPlant = false;
            for(EnumFacing facing:EnumFacing.HORIZONTALS){
                if(MRUtils.isPlant(worldIn.getBlockState(pos.offset(facing)))){
                    hasPlant = true;
                    break;
                }
            }
            if(hasCover){
                worldIn.setBlockState(pos,state.withProperty(STAGE,(hasPlant)?3:0),2);
            }else{
                if(hasSnow){
                    worldIn.setBlockState(pos,state.withProperty(STAGE,(hasPlant)?5:2),2);
                }else{
                    worldIn.setBlockState(pos,state.withProperty(STAGE,(hasPlant)?4:1),2);
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote&&playerIn.isCreative()&&worldIn.isAreaLoaded(pos, 1)&&hand==EnumHand.MAIN_HAND){
            if(playerIn.getHeldItemMainhand().getItem()==MRItems.herbalsBook){
                worldIn.setBlockState(pos, state.withProperty(STAGE, (state.getValue(STAGE)+1)%6), 2);
                return true;
            }
        }
        return false;
    }

}