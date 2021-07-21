package skily_leyu.mistyrain.world.dimension;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import skily_leyu.mistyrain.world.biome.MRBiomeProvider;
import skily_leyu.mistyrain.world.chunk.MRChunkGenerator;

public class WorldTypeMistyRain extends WorldType {

	public WorldTypeMistyRain() {
		super("mr_misty_rain");
	}

	@Override
	public BiomeProvider getBiomeProvider(World world) {
		return new MRBiomeProvider(world.getWorldInfo());
	}

	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new MRChunkGenerator(world, world.getSeed(), generatorOptions);
	}

}
