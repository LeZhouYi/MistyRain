package skily_leyu.mistyrain.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import skily_leyu.mistyrain.config.MRProperty;

public class BlockMRAnimalPlant extends Block{

    public static final PropertyInteger PLANT_STAGE = MRProperty.PLANT_STAGE_HEX;

    public BlockMRAnimalPlant() {
        super(Material.PLANTS);
    }

    @Override
    public int getMetaFromState(IBlockState blockstate){
        return blockstate.getValue(PLANT_STAGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(PLANT_STAGE,meta);
    }

    @Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { PLANT_STAGE });
	}

}
