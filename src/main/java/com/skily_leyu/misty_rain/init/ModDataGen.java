package com.skily_leyu.misty_rain.init;

import com.skily_leyu.misty_rain.MistyRain;
import com.skily_leyu.misty_rain.data.ModBlockStateProvider;
import com.skily_leyu.misty_rain.data.ModBlockTagsProvider;
import com.skily_leyu.misty_rain.data.ModItemModelProvider;
import com.skily_leyu.misty_rain.data.ModLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ModDataGen {

    public static void gatherData(GatherDataEvent event) {
        MistyRain.LOGGER.debug("Gather Data");
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        // 用于TagProvider或RecipeProvider
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //注册BlockStateProvider
        generator.addProvider(
            event.includeServer(),
            new ModBlockStateProvider(output, MistyRain.MOD_ID, existingFileHelper)
        );
        //注册item model
        generator.addProvider(
            event.includeServer(),
            new ModItemModelProvider(output, MistyRain.MOD_ID, existingFileHelper)
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

}
