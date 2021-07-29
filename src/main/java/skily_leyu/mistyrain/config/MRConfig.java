package skily_leyu.mistyrain.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * 用于加载一些可自定义的配置，以及集中一些固定的配置
 * @author Skily
 * @version 1.0.0
 */
public class MRConfig {

	private static Configuration config;
	
	public static int tickSpeed;

	public MRConfig(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		init();
		config.save();
	}

	/**加载所有属性 */
	protected void init() {
		tickSpeed = loadInt("tickSpeed",40);
	}

	/**
	 * 加载属性值为整型的属性
	 * @param key 属性名
	 * @param defaultValue 默认值
	 * @return 返回读取到的属性值
	 */
	protected int loadInt(String key, int defaultValue) {
		return config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getInt();
	}
	
}
