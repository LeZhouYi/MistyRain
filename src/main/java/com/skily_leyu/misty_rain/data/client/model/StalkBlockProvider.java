package com.skily_leyu.misty_rain.data.client.model;

import com.skily_leyu.misty_rain.MistyRain;
import com.skily_leyu.misty_rain.blocks.StalkBlock;
import com.skily_leyu.misty_rain.data.helpers.ModelHelpers;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.Condition;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class StalkBlockProvider extends ModelProvider {
    public StalkBlockProvider(PackOutput output) {
        super(output, MistyRain.MODID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        registerBlockModel(blockModels, MistyRain.BAMBOO_STALK_BLOCK.get(),
            new TextureMapping()
                .put(TextureSlot.SIDE, modLocation("block/bamboo_stalk_side"))
                .put(TextureSlot.END, modLocation("block/bamboo_stalk_side"))
        );
    }

    private void registerBlockModel(BlockModelGenerators blockModels, StalkBlock block, TextureMapping mapping) {
        /*
         * 注册方块模型
         * @param blockModels
         * @param block 要注册模型的方块
         * @param mapping 方块对应的材质映射
         */
        LOGGER.debug("Register stalk block model:{}", block.getDescriptionId());
        ResourceLocation stalkModel = ModelTemplates.STALK.create(block, mapping, blockModels.modelOutput);
        ResourceLocation branchModel = ModelTemplates.STALK_BRANCH.create(block, mapping, blockModels.modelOutput);

        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);
        for (Direction dir : Direction.values()) {
            // 1. 处理本体旋转
            generator.with(
                Condition.condition().term(BlockStateProperties.FACING, dir),
                Variant.variant()
                    .with(VariantProperties.MODEL, stalkModel)
                    .with(VariantProperties.X_ROT, ModelHelpers.getXRot(dir))
                    .with(VariantProperties.Y_ROT, ModelHelpers.getYRot(dir))
            );
            // 2. 处理分枝叠加
            generator.with(
                Condition.condition()
                    .term(BlockStateProperties.FACING, dir)
                    .term(StalkBlock.BRANCHED, true),
                Variant.variant()
                    .with(VariantProperties.MODEL, branchModel)
                    .with(VariantProperties.X_ROT, ModelHelpers.getXRot(dir))
                    .with(VariantProperties.Y_ROT, ModelHelpers.getYRot(dir))
            );
        }
        blockModels.blockStateOutput.accept(generator);
    }

}
