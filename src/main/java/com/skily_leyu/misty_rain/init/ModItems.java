package com.skily_leyu.misty_rain.init;

import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MistyRain.MOD_ID);

    public static final DeferredItem<BlockItem> BAMBOO_STALK_ITEM = ITEMS.registerSimpleBlockItem(
        "bamboo_stalk", ModBlocks.BAMBOO_STALK_BLOCK
    );
    public static final DeferredItem<BlockItem> BAMBOO_BRANCH_ITEM = ITEMS.registerSimpleBlockItem(
        "bamboo_branch", ModBlocks.BAMBOO_BRANCH_BLOCK
    );
    public static final DeferredItem<BlockItem> BAMBOO_STAKE_ITEM = ITEMS.registerSimpleBlockItem(
        "bamboo_stake", ModBlocks.BAMBOO_STAKE_BLOCK
    );
    public static final DeferredItem<BlockItem> BAMBOO_LEAVES_ITEM = ITEMS.registerSimpleBlockItem(
        "bamboo_leaves", ModBlocks.BAMBOO_LEAVES_BLOCK
    );
    public static final DeferredItem<BlockItem> BAMBOO_SHOOT_ITEM = ITEMS.registerSimpleBlockItem(
        "bamboo_shoot", ModBlocks.BAMBOO_SHOOT_BLOCK
    );

}
