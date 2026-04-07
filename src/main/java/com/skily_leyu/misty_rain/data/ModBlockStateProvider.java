package com.skily_leyu.misty_rain.data;

import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, String modID, ExistingFileHelper exFileHelper) {
        super(output, modID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        MistyRain.LOGGER.debug("Register Stalk type blocks");
        registerBambooStalk();
        registerBambooBranch();
        registerBambooStake();
        registerBambooShoot();
        registerBambooLeaves();
    }

    private void registerBambooBranch() {
        ModelFile normalModel = models().withExistingParent(
                "bamboo_branch", modLoc("block/branch")
            ).texture("side", modLoc("block/bamboo_branch_side"))
            .texture("end", modLoc("block/bamboo_stalk_end"));
        ModelFile extendedModel = models().withExistingParent(
                "bamboo_branch_extended", modLoc("block/branch_extended")
            ).texture("side", modLoc("block/bamboo_branch_side"))
            .texture("end", modLoc("block/bamboo_stalk_end"));
        VariantBlockStateBuilder builder = getVariantBuilder(MistyRain.BAMBOO_BRANCH_BLOCK.get());

        for (Direction dir : BlockStateProperties.FACING.getPossibleValues()) {
            int rotateX = dir == Direction.DOWN ? 90 : dir == Direction.UP ? 270 : 0;
            int rotateY = ((int) dir.toYRot() + 180) % 360;
            builder.partialState()
                .with(BlockStateProperties.FACING, dir)
                .with(BlockStateProperties.EXTENDED, false)
                .modelForState()
                .modelFile(normalModel)
                .rotationX(rotateX)
                .rotationY(rotateY)
                .addModel();

            builder.partialState()
                .with(BlockStateProperties.FACING, dir)
                .with(BlockStateProperties.EXTENDED, true)
                .modelForState()
                .modelFile(extendedModel)
                .rotationX(rotateX)
                .rotationY(rotateY)
                .addModel();
        }
    }

    private void registerBambooStalk() {
        ModelFile stalkModel = models().withExistingParent(
                "bamboo_stalk", modLoc("block/stalk")
            ).texture("side", modLoc("block/bamboo_stalk_side"))
            .texture("end", modLoc("block/bamboo_stalk_end"));
        directionalBlock(MistyRain.BAMBOO_STALK_BLOCK.get(), stalkModel);
    }

    private void registerBambooStake() {
        ModelFile baseModel = models().withExistingParent(
                "bamboo_stake", modLoc("block/stalk")
            ).texture("side", modLoc("block/bamboo_stake_side"))
            .texture("end", modLoc("block/bamboo_stalk_end"));
        directionalBlock(MistyRain.BAMBOO_STAKE_BLOCK.get(), baseModel);
    }

    private void registerBambooShoot() {
        ModelFile stage0 = models().withExistingParent("bamboo_shoot_0", "minecraft:block/cross")
            .texture("cross", modLoc("block/bamboo_shoot_0"));
        ModelFile stage1 = models().withExistingParent("bamboo_shoot_1", "minecraft:block/cross")
            .texture("cross", modLoc("block/bamboo_shoot_1"));
        getVariantBuilder(MistyRain.BAMBOO_SHOOT_BLOCK.get())
            .partialState().with(BlockStateProperties.AGE_1, 0)
            .modelForState().modelFile(stage0).addModel()
            .partialState().with(BlockStateProperties.AGE_1, 1)
            .modelForState().modelFile(stage1).addModel();
    }

    private void registerBambooLeaves() {
        ModelFile model = models().withExistingParent("bamboo_leaves", "minecraft:block/cube_all")
            .texture("all", modLoc("block/bamboo_stake_side"));
        simpleBlock(MistyRain.BAMBOO_LEAVES_BLOCK.get(), model);
    }
}
