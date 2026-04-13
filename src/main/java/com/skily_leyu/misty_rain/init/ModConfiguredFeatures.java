package com.skily_leyu.misty_rain.init;

import com.skily_leyu.misty_rain.MistyRain;
import com.skily_leyu.misty_rain.world.tree.BambooTreeTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModConfiguredFeatures {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACES =
        DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, MistyRain.MOD_ID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<BambooTreeTrunkPlacer>> BAMBOO_TREE =
        TRUNK_PLACES.register("bamboo_tree", ()->new TrunkPlacerType<>(BambooTreeTrunkPlacer.CODEC));
}
