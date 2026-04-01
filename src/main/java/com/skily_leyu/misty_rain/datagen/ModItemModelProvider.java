package com.skily_leyu.misty_rain.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, String modID, ExistingFileHelper existingFileHelper) {
        super(output, modID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("bamboo_stalk", modLoc("block/bamboo_stalk_base"));
    }
}
