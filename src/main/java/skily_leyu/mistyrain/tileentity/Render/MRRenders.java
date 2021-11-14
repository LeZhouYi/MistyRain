package skily_leyu.mistyrain.tileentity.Render;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import skily_leyu.mistyrain.tileentity.MRTileEntityPot;

public class MRRenders {

    public MRRenders(FMLPreInitializationEvent event) {
		ClientRegistry.bindTileEntitySpecialRenderer(MRTileEntityPot.class, new MRRenderPot());
	}
}
