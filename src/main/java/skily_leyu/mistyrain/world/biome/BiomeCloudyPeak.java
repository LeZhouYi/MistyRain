package skily_leyu.mistyrain.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class BiomeCloudyPeak extends Biome {

	public static Season season
	public static IBlockstate surface = MRBlocks.springMud.getDefaultState();

	public BiomeCloudyPeak(BiomeProperties properties, BiomeDecorator decorator) {
		super(properties);
		if (decorator != null) {
			this.decorator = decorator;
		}
	}

}
