package skily_leyu.mistyrain.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import skily_leyu.mistyrain.MistyRain;

public class MRBiomes {

	public static final Biome biomeCloudyPeak = new BiomeCloudyForest(new BiomeProperties("mr_cloudy_peak")
			.setBaseHeight(-0.25F).setHeightVariation(0.015F).setTemperature(0.6F).setRainfall(0.85F), null)
					.setRegistryName(MistyRain.MODID, "cloudy_peak");

	@Mod.EventBusSubscriber
	public static class ObjectRegistryHandler {
		@SubscribeEvent
		public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
			final IForgeRegistry<Biome> registry = event.getRegistry();
			MistyRain.getLogger().info("Registrying biomes[MistyRain]");
			registry.register(biomeCloudyPeak);
		}
	}

}
