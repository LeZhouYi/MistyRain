package com.skily_leyu.misty_rain.init;

import com.skily_leyu.misty_rain.MistyRain;
import com.skily_leyu.misty_rain.blocks.*;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MistyRain.MOD_ID);

    public static final DeferredBlock<BambooStalkBlock> BAMBOO_STALK_BLOCK = BLOCKS.registerBlock("bamboo_stalk",
        (properties) -> new BambooStalkBlock(properties
            .mapColor(MapColor.WOOD)
            .strength(2.0f, 2.0f)
            .sound(SoundType.BAMBOO)
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
        ));
    public static final DeferredBlock<BambooBranchBlock> BAMBOO_BRANCH_BLOCK = BLOCKS.registerBlock("bamboo_branch",
        (properties) -> new BambooBranchBlock(properties
            .mapColor(MapColor.COLOR_GREEN)
            .strength(1.0F, 1.0F)
            .sound(SoundType.BAMBOO)
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .isViewBlocking((state, getter, pos) -> false)
            .isSuffocating((state, getter, pos) -> false)
        ));
    public static final DeferredBlock<BambooStakeBlock> BAMBOO_STAKE_BLOCK = BLOCKS.registerBlock("bamboo_stake",
        (properties) -> new BambooStakeBlock(properties
            .mapColor(MapColor.WOOD)
            .strength(2.0f, 2.0f)
            .sound(SoundType.BAMBOO)
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
        ));
    public static final DeferredBlock<BambooLeavesBlock> BAMBOO_LEAVES_BLOCK = BLOCKS.registerBlock("bamboo_leaves",
        (properties) -> new BambooLeavesBlock(properties
            .mapColor(MapColor.COLOR_GREEN)
            .strength(0.2f, 0.2f)
            .sound(SoundType.GRASS)
            .noOcclusion()
            .isViewBlocking((state, getter, pos) -> false)
            .isSuffocating((state, getter, pos) -> false)
        ));
    public static final DeferredBlock<BambooShootBlock> BAMBOO_SHOOT_BLOCK = BLOCKS.registerBlock("bamboo_shoot",
        properties -> new BambooShootBlock(properties
            .mapColor(MapColor.TERRACOTTA_GREEN)
            .strength(1.5f, 1.5f)
            .sound(SoundType.BAMBOO_SAPLING)
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .noCollission()
            .isViewBlocking((state, getter, pos) -> false)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .isSuffocating((state, getter, pos) -> false)
            .randomTicks()
        ));

}
