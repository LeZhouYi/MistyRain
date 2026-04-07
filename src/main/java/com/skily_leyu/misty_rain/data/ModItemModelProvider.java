package com.skily_leyu.misty_rain.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, String modID, ExistingFileHelper existingFileHelper) {
        super(output, modID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("bamboo_stalk", modLoc("block/bamboo_stalk"));
        withExistingParent("bamboo_branch", modLoc("block/bamboo_branch"));
        withExistingParent("bamboo_stake", modLoc("block/bamboo_stake"));
        withExistingParent("bamboo_leaves", modLoc("block/bamboo_leaves"));
        withExistingParent("bamboo_shoot", "minecraft:item/generated")
            .texture("layer0", modLoc("block/bamboo_shoot_1"));
    }

}
