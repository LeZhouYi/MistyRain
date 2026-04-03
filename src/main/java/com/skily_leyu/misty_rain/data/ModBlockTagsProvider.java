package com.skily_leyu.misty_rain.data;

import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MistyRain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        //叶子被剪逻辑
        this.tag(TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("mineable/shears")))
            .add(MistyRain.BAMBOO_LEAVES_BLOCK.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
            .add(MistyRain.BAMBOO_STAKE_BLOCK.get())
            .add(MistyRain.BAMBOO_BRANCH_BLOCK.get())
            .add(MistyRain.BAMBOO_STALK_BLOCK.get());

        this.tag(BlockTags.SWORD_EFFICIENT)
            .add(MistyRain.BAMBOO_BRANCH_BLOCK.get())
            .add(MistyRain.BAMBOO_LEAVES_BLOCK.get());
    }
}
