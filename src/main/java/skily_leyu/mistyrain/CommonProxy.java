package skily_leyu.mistyrain;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import skily_leyu.mistyrain.client.gui.MRGuis;
import skily_leyu.mistyrain.command.MRCommands;
import skily_leyu.mistyrain.config.MRSettings;
import skily_leyu.mistyrain.tileentity.MRTileEntities;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		new MRSettings(event);
		new MRGuis();
		new MRTileEntities(event);
	}

	public void init(FMLInitializationEvent event) {
	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public void serverStarting(FMLServerStartingEvent event) {
		new MRCommands(event);
	}

}
