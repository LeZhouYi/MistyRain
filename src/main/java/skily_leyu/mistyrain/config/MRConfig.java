package skily_leyu.mistyrain.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import skily_leyu.mistyrain.basic.type.SolarTerm;

/**
 * 用于加载一些可自定义的配置，以及集中一些固定的配置
 * 
 * @author Skily
 * @version 1.0.0
 */
public class MRConfig {

	// -----------------内置属性------------------
	public static int peakDepth = 4;// 山峰填充深度
	public static int peakGenTime = 16;// 生成山峰次数
	public static int peakTopBound = 20;// 山峰顶点最大偏移值
	public static int peakMaxGenHeight = 128; // 只在该高度以下生成山峰，过高容易重叠
	public static int[] peakRadius = new int[] {5,20}; //山峰的直径
	public static int[] peakHeight = new int[] {64,128}; //山峰的高度
	public static int[] peakPointNum = new int[]{1,3}; //山峰异形点的数量 
	
	public static float peakGenRate = 0.5F;// 生成山峰概率
	public static float peakCheckRate = 0.75F; // 检查基础的范围，0=只检查一个方块
	public static float[] peakFloor = new float[0.10F,0.25F];// 山峰层级厚度
	public static float[] peakWeight= new float[]{1.0F,2.75F}; //图形在XZ方向的权重，影响基本形状
	public static float[] peakGradient = new float[]{0.5F,0.9F}; // 最小缩放格数
	public static float[] peakMixRate = new float[] { 0.3F, 0.6F }; // 山峰图格混合比率
	public static float[] peakCellRate = new float[] { 0.25F, 0.35F, 0.75F, 0.85F, 1.0F };// 山峰图格比率
	public static float[] peakPointRadius = new float[]{0.5F,0.75F}; //山峰异形点计算范围
	public static float[] peakPointRate = new float[]{1.25F,1.5F}; //山峰异形点计算权重

	private static Configuration config;

	public static int tickSpeed; // 植物生长更新Tick
	public static float growRate; // 植物生长相关的基础概率
	public static float dropRate; //植物掉落物品的基础概率

	public static int solarTermDays; // 时令持续天数
	public static int solarTermStart; // 游戏初始的时令

	// --------------根据获得的属性来计算获得的属性----------------------
	public static float monthStart;// 游戏初始日期
	public static int monthDays;// 一月持续天
	public static float baseGrowRate; //植物生长相关的基础概率

	public MRConfig(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		init();
		afterInit();
		config.save();
	}

	protected void afterInit() {
		monthStart = SolarTerm.values()[MRConfig.solarTermStart].getStart();
		monthDays = MRConfig.solarTermDays * 2;
		baseGrowRate = growRate/monthDays;
	}

	/** 加载所有属性 */
	protected void init() {
		tickSpeed = loadInt("tickSpeed", 40);
		
		growRate = loadFloat("growRate", 0.1F);
		dropRate = loadFloat("dropRate", 0.1F);

		solarTermDays = loadInt("solarTermDays", 15);
		solarTermStart = loadInt("solarTermStart", 0);
	}

	/**
	 * 加载属性值为整型的属性
	 * 
	 * @param key          属性名
	 * @param defaultValue 默认值
	 * @return 返回读取到的属性值
	 */
	protected int loadInt(String key, int defaultValue) {
		return config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getInt();
	}

	/**
	 * 加载属性值为Float的属性
	 * 
	 * @param key          属性名
	 * @param defaultValue 默认值
	 * @return 返回读取到的属性值
	 */
	protected float loadFloat(String key, float defaultValue) {
		return (float) config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getDouble();
	}

}
