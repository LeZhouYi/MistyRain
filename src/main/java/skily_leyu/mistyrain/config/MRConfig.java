package skily_leyu.mistyrain.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class MRConfig {

	private static Configuration config;

	public static int cloudyForestWaterDepth;
	public static int cloudyForestSoilHeight;
	
	public MRConfig(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		init();
		config.save();
	}

	protected void init() {
		cloudyForestWaterDepth = loadInt("cloudyForestWaterDepth", 3);
		cloudyForestSoilHeight = loadInt("cloudyForestSoilHeight", 4);
	}

	protected int loadInt(String key, int defalutValue) {
		return config.get(Configuration.CATEGORY_GENERAL, key, defalutValue).getInt();
	}
	
}
