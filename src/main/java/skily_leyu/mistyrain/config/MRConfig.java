package skily_leyu.mistyrain.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * 用于加载一些可自定义的配置，以及集中一些固定的配置
 * @author Skily
 * @version 1.0.0
 */
public class MRConfig {

	//-----------------内置属性------------------
	public static int cloudPeakRadius=16;
	public static int cloudPeakCheckTime=16;//生成山峰次数
	public static float cloudPeakGenRate=0.25F;//生成山峰概率
	public static int cloudPeakGenHeight=128; //只在该高度以下生成山峰，过高容易重叠
	public static int cloudPeakMinHeight=64; //山峰最小高度
	public static int cloudPeakMaxHeight=128; //山峰最大高度
	public static float cloudPeakMinGradient=5; //最小缩放格数
	public static float cloudPeakMaxGradient=10; //最大缩放格数
	public static float cloudPeakDepth=4;//山峰填充深度
	public static float cloudPeakTopBound=16；//山峰顶点最大偏移值
	public static float[] cloudPeakCellRate = new float[]{0.25F,0.35F,0.75F,0.85F,1.0F};//山峰图格比率
	public static float[] cloudPeakMixRate = new float[]{0.3F,0.6F}; //山峰图格混合比率
	public static float cloudCheckCheckRate = 0.75F; //检查基础的范围，0=只检查一个方块

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

	/**
	 * 加载属性值为Float的属性
	 * @param key 属性名
	 * @param defaultValue 默认值
	 * @return 返回读取到的属性值
	 */
	protected float loadFloat(String key, float defaultValue) {
		return (float) config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getDouble();
	}

}
