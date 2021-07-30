package skily_leyu.mistyrain.world.decorator;

import net.minecraft.world.biome.BiomeDecorator;

public class DecoratorCloudPeak extends BiomeDecorator{
    
    @Override
	public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
		if (this.decorating) {
			throw new RuntimeException("Already decorating");
		} else {
			this.chunkProviderSettings = ChunkGeneratorSettings.Factory
					.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
			this.chunkPos = pos;
			this.genDecorations(biome, worldIn, random);
			this.decorating = false;
		}
	}

    @Override
	protected void genDecorations(Biome biomeIn, World worldIn, Random random) {
		net.minecraft.util.math.ChunkPos forgeChunkPos = new net.minecraft.util.math.ChunkPos(chunkPos);
    }

}
