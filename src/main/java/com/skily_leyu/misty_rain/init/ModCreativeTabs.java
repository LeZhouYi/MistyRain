package com.skily_leyu.misty_rain.init;

import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MistyRain.MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MISTY_RAIN_TAB =
        CREATIVE_MODE_TABS.register("misty_rain_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.misty_rain"))
            .icon(() -> ModItems.BAMBOO_SHOOT_ITEM.get().getDefaultInstance())
            .build());

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == MISTY_RAIN_TAB.getKey()) {
            event.accept(ModItems.BAMBOO_STALK_ITEM);
            event.accept(ModItems.BAMBOO_BRANCH_ITEM);
            event.accept(ModItems.BAMBOO_STAKE_ITEM);
            event.accept(ModItems.BAMBOO_LEAVES_ITEM);
            event.accept(ModItems.BAMBOO_SHOOT_ITEM);
        }
    }
}
