package com.skily_leyu.misty_rain.datagen;

import com.skily_leyu.misty_rain.MistyRain;
import com.skily_leyu.misty_rain.blocks.StalkBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, String modID, ExistingFileHelper exFileHelper) {
        super(output, modID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        MistyRain.LOGGER.debug("Register Stalk type blocks");
        registerStalkBlockModel(MistyRain.BAMBOO_STALK_BLOCK.get(), "bamboo");
    }

    @SuppressWarnings("SameParameterValue")
    private void registerStalkBlockModel(StalkBlock block, String prefix) {
        //注册竿模型的方块
        ResourceLocation sideLoc = modLoc(String.format("block/%s_stalk_side", prefix));
        ResourceLocation endLoc = modLoc(String.format("block/%s_stalk_end", prefix));
        ModelFile stalkModel = models().withExistingParent(
                String.format("%s_stalk_base", prefix), modLoc("block/stalk")
            ).texture("side", sideLoc)
            .texture("end", endLoc);
        ModelFile stalkBranchModel = models().withExistingParent(
            String.format("%s_stalk_branch", prefix), modLoc("block/stalk_branch")
        ).texture("end", endLoc);
        var builder = getMultipartBuilder(block);
        for (Direction direction : Direction.values()) {
            int xRot = ModelHelpers.getXRot(direction);
            int yRot = ModelHelpers.getYRot(direction);
            builder.part()
                .modelFile(stalkModel)
                .rotationX(xRot)
                .rotationY(yRot)
                .addModel()
                .condition(StalkBlock.FACING, direction)
                .end();
            builder.part()
                .modelFile(stalkBranchModel)
                .rotationX(xRot)
                .rotationY(yRot)
                .addModel()
                .condition(StalkBlock.FACING, direction)
                .condition(StalkBlock.BRANCHED, true)
                .end();
        }
    }
}
