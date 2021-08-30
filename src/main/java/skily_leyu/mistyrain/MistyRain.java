package skily_leyu.mistyrain;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import skily_leyu.mistyrain.config.MRConfig;

@Mod(modid = MistyRain.MODID, name = MistyRain.NAME, version = MistyRain.VERSION, acceptedMinecraftVersions = "1.12.2")
public class MistyRain {

	public static final String MODID = "mistyrain";
	public static final String NAME = "Misty Rain";
	public static final String VERSION = "0.0.0";

	private static Logger logger;

	@Instance(MistyRain.MODID)
	public static MistyRain instance;

    @SidedProxy(clientSide = "skily_leyu.mistyrain.ClientProxy",
            serverSide = "skily_leyu.mistyrain.CommonProxy")
    public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
		new MRConfig(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}

	public static Logger getLogger() {
		return logger;
	}
}
