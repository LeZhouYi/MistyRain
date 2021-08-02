package skily_leyu.mistyrain.world.biome.decorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.util.math.ChunkPos;

import skily_leyu.mistyrain.basic.MathUtils;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.world.biome.generator.GeneratorMRPeak;

public class DecoratorCloudPeak extends BiomeDecorator {

	private WorldGenerator peakGenerator;

	public DecoratorCloudPeak() {
		List<IBlockState> blockList = new ArrayList<>();
		blockList.add(Blocks.AIR.getDefaultState());
		blockList.add(Blocks.DIRT.getDefaultState());
		blockList.add(Blocks.STONE.getDefaultState());
		blockList.add(Blocks.LOG.getDefaultState());
		blockList.add(Blocks.GRASS.getDefaultState());
		this.peakGenerator = new GeneratorMRPeak(blockList);
	}

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
		ChunkPos forgeChunkPos = new ChunkPos(chunkPos);
		for (int index = 0; index < MRConfig.peakGenTime; index++) {
			if (MathUtils.canDo(random, MRConfig.peakGenRate)) {
				int x = random.nextInt(16) + 8;
				int z = random.nextInt(16) + 8;
				BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(x, 0, z));
				peakGenerator.generate(worldIn, random, blockpos);
			}
		}

		if (net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos,
				net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE)) {

		}

	}

}
