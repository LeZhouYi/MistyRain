package com.skily_leyu.misty_rain.data;

import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.data.PackOutput;
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
        registerBambooStalk();
        registerBambooBranch();
    }

    private void registerBambooBranch() {
        ModelFile branchModel = models().withExistingParent(
                "bamboo_branch", modLoc("block/branch")
            ).texture("side", modLoc("block/bamboo_branch_side"))
            .texture("end", modLoc("block/bamboo_stalk_end"));
        directionalBlock(MistyRain.BAMBOO_BRANCH_BLOCK.get(), branchModel);
    }

    private void registerBambooStalk() {
        ModelFile stalkModel = models().withExistingParent(
                "bamboo_stalk", modLoc("block/stalk")
            ).texture("side", modLoc("block/bamboo_stalk_side"))
            .texture("end", modLoc("block/bamboo_stalk_end"));
        directionalBlock(MistyRain.BAMBOO_STALK_BLOCK.get(), stalkModel);
    }
}
