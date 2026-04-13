package com.skily_leyu.misty_rain;

import com.mojang.logging.LogUtils;
import com.skily_leyu.misty_rain.init.ModBlocks;
import com.skily_leyu.misty_rain.init.ModCreativeTabs;
import com.skily_leyu.misty_rain.init.ModDataGen;
import com.skily_leyu.misty_rain.init.ModItems;
import com.skily_leyu.misty_rain.init.ModConfiguredFeatures;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(MistyRain.MOD_ID)
public class MistyRain {
    public static final String MOD_ID = "misty_rain";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MistyRain(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(ModCreativeTabs::addCreative);
        modEventBus.addListener(ModDataGen::gatherData);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModConfiguredFeatures.TRUNK_PLACES.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }
}
