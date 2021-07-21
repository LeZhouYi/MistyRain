package skily_leyu.mistyrain.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class BiomeCloudyForest extends Biome {

	public BiomeCloudyForest(BiomeProperties properties, BiomeDecorator decorator) {
		super(properties);
		if (decorator != null) {
			this.decorator = decorator;
		}
	}

}
