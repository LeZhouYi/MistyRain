package skily_leyu.mistyrain.network;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import skily_leyu.mistyrain.MistyRain;

public class MRMessages {

    private static int id = 0;
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MistyRain.MODID);

    public MRMessages(FMLPreInitializationEvent event){
        INSTANCE.registerMessage(MessageMRPot.Handler.class, MessageMRPot.class, id++, Side.SERVER);
    }
}
