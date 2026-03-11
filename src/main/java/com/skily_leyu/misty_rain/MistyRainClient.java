package com.skily_leyu.misty_rain;

import com.skily_leyu.misty_rain.data.client.BranchedLogBlockProvider;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(value = MistyRain.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = MistyRain.MODID, value = Dist.CLIENT)
public class MistyRainClient {
    public MistyRainClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        MistyRain.LOGGER.info("HELLO FROM CLIENT SETUP");
        MistyRain.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
