package com.skily_leyu.misty_rain.data.client.model;

import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.extensions.IModelProviderExtension;

import java.util.Optional;

@Mod(MistyRain.MODID)
public class ModelTemplates implements IModelProviderExtension {

    public static final ModelTemplate STALK;
    public static final ModelTemplate STALK_BRANCH;

    static {
        STALK = new ModelTemplate(
            Optional.of(ResourceLocation.fromNamespaceAndPath(MistyRain.MODID, "block/stalk")),
            Optional.empty(),
            TextureSlot.END, TextureSlot.SIDE);
        STALK_BRANCH = new ModelTemplate(
            Optional.of(ResourceLocation.fromNamespaceAndPath(MistyRain.MODID, "block/stalk_branch")),
            Optional.empty(),
            TextureSlot.END, TextureSlot.SIDE);
    }

}
