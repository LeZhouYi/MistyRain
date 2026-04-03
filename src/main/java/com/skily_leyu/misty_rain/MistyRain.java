package com.skily_leyu.misty_rain;

import com.mojang.logging.LogUtils;
import com.skily_leyu.misty_rain.blocks.BambooBranchBlock;
import com.skily_leyu.misty_rain.blocks.BambooLeavesBlock;
import com.skily_leyu.misty_rain.blocks.BambooStakeBlock;
import com.skily_leyu.misty_rain.blocks.BambooStalkBlock;
import com.skily_leyu.misty_rain.data.ModBlockStateProvider;
import com.skily_leyu.misty_rain.data.ModBlockTagsProvider;
import com.skily_leyu.misty_rain.data.ModItemModelProvider;
import com.skily_leyu.misty_rain.data.ModLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(MistyRain.MOD_ID)
public class MistyRain {
    public static final String MOD_ID = "misty_rain";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredBlock<BambooStalkBlock> BAMBOO_STALK_BLOCK = BLOCKS.registerBlock("bamboo_stalk",
        (properties) -> new BambooStalkBlock(properties
            .mapColor(MapColor.WOOD)
            .strength(2.0f, 2.0f)
            .sound(SoundType.BAMBOO)
            .noOcclusion()
        ));
    public static final DeferredBlock<BambooBranchBlock> BAMBOO_BRANCH_BLOCK = BLOCKS.registerBlock("bamboo_branch",
        (properties) -> new BambooBranchBlock(properties
            .mapColor(MapColor.COLOR_GREEN)
            .strength(1.0F, 1.0F)
            .sound(SoundType.BAMBOO)
            .noOcclusion()
        ));
    public static final DeferredBlock<BambooStakeBlock> BAMBOO_STAKE_BLOCK = BLOCKS.registerBlock("bamboo_stake",
        (properties) -> new BambooStakeBlock(properties
            .mapColor(MapColor.WOOD)
            .strength(2.0f, 2.0f)
            .sound(SoundType.BAMBOO)
            .noOcclusion()
        ));
    public static final DeferredBlock<BambooLeavesBlock> BAMBOO_LEAVES_BLOCK = BLOCKS.registerBlock("bamboo_leaves",
        (properties) -> new BambooLeavesBlock(properties
            .mapColor(MapColor.COLOR_GREEN)
            .strength(0.2f, 0.2f)
            .sound(SoundType.GRASS)
            .noOcclusion()
        ));

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredItem<BlockItem> BAMBOO_STALK_ITEM = ITEMS.registerSimpleBlockItem(
        "bamboo_stalk", BAMBOO_STALK_BLOCK
    );
    public static final DeferredItem<BlockItem> BAMBOO_BRANCH_ITEM = ITEMS.registerSimpleBlockItem(
        "bamboo_branch", BAMBOO_BRANCH_BLOCK
    );
    public static final DeferredItem<BlockItem> BAMBOO_STAKE_ITEM = ITEMS.registerSimpleBlockItem(
        "bamboo_stake", BAMBOO_STAKE_BLOCK
    );
    public static final DeferredItem<BlockItem> BAMBOO_LEAVES_ITEM = ITEMS.registerSimpleBlockItem(
        "bamboo_leaves", BAMBOO_LEAVES_BLOCK
    );

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
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
        modEventBus.addListener(this::gatherData);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void gatherData(GatherDataEvent event) {
        LOGGER.debug("Gather Data");
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        // 用于TagProvider或RecipeProvider
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //注册BlockStateProvider
        generator.addProvider(
            event.includeServer(),
            new ModBlockStateProvider(output, MOD_ID, existingFileHelper)
        );
        //注册item model
        generator.addProvider(
            event.includeServer(),
            new ModItemModelProvider(output, MOD_ID, existingFileHelper)
        );
        //注册tag，须先于战利品表
        generator.addProvider(event.includeServer(), new ModBlockTagsProvider(
            output,
            lookupProvider,
            existingFileHelper
        ));
        //注册战利品表
        generator.addProvider(event.includeServer(),
            new ModLootTableProvider(output, lookupProvider));
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == MISTY_RAIN_TAB.getKey()) {
            event.accept(BAMBOO_STALK_ITEM);
            event.accept(BAMBOO_BRANCH_ITEM);
            event.accept(BAMBOO_STAKE_ITEM);
            event.accept(BAMBOO_LEAVES_ITEM);
        }
    }
}
