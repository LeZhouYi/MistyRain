package skily_leyu.mistyrain;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import skily_leyu.mistyrain.client.gui.MRGuis;
import skily_leyu.mistyrain.config.MRSettings;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		new MRSettings(event);
		new MRGuis();
	}

	public void init(FMLInitializationEvent event) {
	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public void serverStarting(FMLServerStartingEvent event) {
	}

}
