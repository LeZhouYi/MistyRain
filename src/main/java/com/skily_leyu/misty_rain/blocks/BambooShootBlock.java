package com.skily_leyu.misty_rain.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class BambooShootBlock extends Block implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;

    public static final MapCodec<BambooShootBlock> CODEC = simpleCodec(BambooShootBlock::new);

    public BambooShootBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
            .setValue(AGE, 0));
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true; // 是否可以施肥
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return (double)random.nextFloat() < 0.45D; // 施肥成功的概率
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        this.performGrowth(level, random, pos, state);
    }

    @Override
    protected void randomTick(@NotNull BlockState state, ServerLevel level, BlockPos pos, @NotNull RandomSource random) {
        // 检查亮度，原版树苗要求亮度 >= 9
        if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
            boolean isRaining = level.isRainingAt(pos);
            int chanceDenominator = isRaining ? 4 : 10;
            if (random.nextInt(chanceDenominator) == 0) {
                this.performGrowth(level, random, pos, state);
            }
        }
    }

    private void performGrowth(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.getValue(AGE) == 0) {
            // 第一阶段：进入准备生长状态
            level.setBlock(pos, state.cycle(AGE), 4);
        } else {
            // 第二阶段：尝试生成树木
            this.growTree(level, pos, random);
        }
    }

//    private void performGrowth(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
//        if (state.getValue(STAGE) == 0) {
//            // 第一阶段：进入准备生长状态
//            level.setBlock(pos, state.cycle(STAGE), 4);
//        } else {
//            // 第二阶段：尝试生成树木
//            this.growTree(level, pos, random);
//        }
//    }
//
    private void growTree(ServerLevel level, BlockPos pos, RandomSource random) {
        // 获取你在数据包 (JSON) 或注册表中定义的树木配置
        // ResourceKey<ConfiguredFeature<?, ?>> treeKey = ModConfiguredFeatures.MY_TREE_KEY;

//        level.registryAccess().registry(Registries.CONFIGURED_FEATURE)
//            .flatMap(reg -> reg.getHolder(ModConfiguredFeatures.MY_TREE_KEY))
//            .ifPresent(holder -> {
//                // 1. 先移除当前方块（模拟树苗消失）
//                level.removeBlock(pos, false);
//
//                // 2. 放置 Feature
//                if (!holder.value().place(level, level.getChunkSource().getGenerator(), random, pos)) {
//                    // 如果生成失败（例如空间不足），把方块放回去
//                    level.setBlock(pos, state, 4);
//                }
//            });
    }
}
