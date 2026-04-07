package com.skily_leyu.misty_rain.data;

import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootSubProvider extends BlockLootSubProvider {
    protected ModBlockLootSubProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        //掉落自身
        this.dropSelf(MistyRain.BAMBOO_STALK_BLOCK.get());
        this.dropSelf(MistyRain.BAMBOO_SHOOT_BLOCK.get());

        //不精准会掉落其它物品
        Block bambooStakeBlock = MistyRain.BAMBOO_STAKE_BLOCK.get();
        this.add(bambooStakeBlock,
            this.createSilkTouchDispatchTable(
                bambooStakeBlock,
                this.applyExplosionDecay(bambooStakeBlock, LootItem.lootTableItem(MistyRain.BAMBOO_STALK_ITEM.get()))
            )
        );

        //只有精准才掉落
        this.add(MistyRain.BAMBOO_BRANCH_BLOCK.get(),
            this.createSilkTouchOnlyTable(MistyRain.BAMBOO_BRANCH_ITEM.get()));

        //叶子掉落逻辑
        this.add(MistyRain.BAMBOO_LEAVES_BLOCK.get(), LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(MistyRain.BAMBOO_LEAVES_ITEM.get())
                    .when(AnyOfCondition.anyOf(HAS_SHEARS, hasSilkTouch()))
                )
            )
        );
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        // 返回该 Mod 中所有需要生成掉落表的方块列表
        return MistyRain.BLOCKS.getEntries().stream().map(Holder::value).collect(Collectors.toList());
    }
}
