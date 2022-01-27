package skily_leyu.mistyrain.client.render;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import skily_leyu.mistyrain.tileentity.TileEntityMRPot;

public class MRRenders {

    public MRRenders(FMLPreInitializationEvent event) {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMRPot.class, new RenderMRPot());
	}

}
