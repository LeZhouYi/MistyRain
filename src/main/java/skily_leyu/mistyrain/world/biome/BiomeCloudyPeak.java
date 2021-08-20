package skily_leyu.mistyrain.world.biome;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import skily_leyu.mistyrain.block.MRBlocks;

public class BiomeCloudyPeak extends Biome {

	public static IBlockState surface = MRBlocks.springMud.getDefaultState();

	public BiomeCloudyPeak(BiomeProperties properties, BiomeDecorator decorator) {
		super(properties);
		if (decorator != null) {
			this.decorator = decorator;
		}
	}

}
