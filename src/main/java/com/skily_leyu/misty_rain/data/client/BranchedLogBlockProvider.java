package com.skily_leyu.misty_rain.data.client;

import com.skily_leyu.misty_rain.MistyRain;
import com.skily_leyu.misty_rain.blocks.BranchedLogBlock;
import com.skily_leyu.misty_rain.data.helpers.ModelHelpers;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.Condition;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class BranchedLogBlockProvider extends ModelProvider {
    public BranchedLogBlockProvider(PackOutput output) {
        super(output, MistyRain.MODID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        createCustomBlock(blockModels, MistyRain.BAMBOO_STALK_BLOCK.get());
    }

    private void createCustomBlock(BlockModelGenerators blockModels, BranchedLogBlock block) {
        ResourceLocation baseModelRes = modLocation("block/bamboo_stalk");
        ResourceLocation branchModelRes = modLocation("block/bamboo_branch");
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);
        for (Direction dir : Direction.values()) {
            // 1. 处理本体旋转
            generator.with(
                    Condition.condition().term(BlockStateProperties.FACING, dir),
                    Variant.variant()
                            .with(VariantProperties.MODEL, baseModelRes)
                            .with(VariantProperties.X_ROT, ModelHelpers.getXRot(dir))
                            .with(VariantProperties.Y_ROT, ModelHelpers.getYRot(dir))
            );
            // 2. 处理分枝叠加
            generator.with(
                    Condition.condition()
                            .term(BlockStateProperties.FACING, dir)
                            .term(BranchedLogBlock.BRANCHED, true),
                    Variant.variant()
                            .with(VariantProperties.MODEL, branchModelRes)
                            .with(VariantProperties.X_ROT, ModelHelpers.getXRot(dir))
                            .with(VariantProperties.Y_ROT, ModelHelpers.getYRot(dir))
            );
        }
        blockModels.blockStateOutput.accept(generator);
    }
}
