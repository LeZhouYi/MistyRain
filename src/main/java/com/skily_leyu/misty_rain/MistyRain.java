package com.skily_leyu.misty_rain;

import com.mojang.logging.LogUtils;
import com.skily_leyu.misty_rain.blocks.StalkBlock;
import com.skily_leyu.misty_rain.data.Loot.BlockLootProvider;
import com.skily_leyu.misty_rain.data.client.model.StalkBlockProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

@Mod(MistyRain.MODID)
public class MistyRain {
    public static final String MODID = "misty_rain";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredBlock<StalkBlock> BAMBOO_STALK_BLOCK = BLOCKS.registerBlock("bamboo_stalk",
        (properties) -> new StalkBlock(properties
            .mapColor(MapColor.WOOD)
            .strength(2.0f, 2.0f)
            .sound(SoundType.BAMBOO)
        ));
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredItem<BlockItem> BAMBOO_STALK_ITEM = ITEMS.registerSimpleBlockItem(
        "bamboo_stalk", BAMBOO_STALK_BLOCK
    );
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MISTY_RAIN_TAB =
        CREATIVE_MODE_TABS.register("misty_rain_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.misty_rain"))
            .icon(() -> BAMBOO_STALK_ITEM.get().getDefaultInstance())
            .build());

    public MistyRain(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::gatherClientData);
        modEventBus.addListener(this::gatherServerData);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public void gatherClientData(GatherDataEvent.Client event) {
        // 处理客户端数据生成 (如模型)
        event.createProvider(StalkBlockProvider::new);
    }

    public void gatherServerData(GatherDataEvent.Client event) {
        // 处理服务器数据生成 (配方、标签等)
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        generator.addProvider(true, new LootTableProvider(
            packOutput,
            Collections.emptySet(),
            List.of(new LootTableProvider.SubProviderEntry(
                BlockLootProvider::new,
                LootContextParamSets.BLOCK
            )),
            lookupProvider
        ));
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == MISTY_RAIN_TAB.getKey()) {
            event.accept(BAMBOO_STALK_ITEM);
        }
    }
}
