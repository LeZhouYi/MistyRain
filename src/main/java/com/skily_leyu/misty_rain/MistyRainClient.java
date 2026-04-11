package com.skily_leyu.misty_rain;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = MistyRain.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = MistyRain.MOD_ID, value = Dist.CLIENT)
public class MistyRainClient {
    public MistyRainClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        MistyRain.LOGGER.info("HELLO FROM CLIENT SETUP");
    }
}
