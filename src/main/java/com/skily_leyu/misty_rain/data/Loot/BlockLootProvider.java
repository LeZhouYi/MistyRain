package com.skily_leyu.misty_rain.data.Loot;

import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public class BlockLootProvider extends BlockLootSubProvider {

    public BlockLootProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlagSet.of(), registries);
    }

    @Override
    protected void generate() {
        this.dropSelf(MistyRain.BAMBOO_STALK_BLOCK.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return MistyRain.BLOCKS.getEntries().stream()
            .map(DeferredHolder::get)
            .collect(Collectors.toList());
    }
}
