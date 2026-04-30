package com.skily_leyu.misty_rain.world.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skily_leyu.misty_rain.init.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class BambooTreeTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<BambooTreeTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
        trunkPlacerParts(instance).and(
            instance.group(
                BlockStateProvider.CODEC.fieldOf("branch_provider").forGetter(tp -> tp.branchProvider),
                Codec.INT.fieldOf("stages").forGetter(tp -> tp.stages)
            )
        ).apply(instance, BambooTreeTrunkPlacer::new));

    private final BlockStateProvider branchProvider;
    private final int stages;

    public BambooTreeTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, BlockStateProvider branchProvider,
                                 int stages) {
        super(baseHeight, heightRandA, heightRandB);
        this.branchProvider = branchProvider;
        this.stages = stages;
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type() {
        return ModConfiguredFeatures.BAMBOO_TREE.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> blockSetter, @NotNull RandomSource random, int freeTreeHeight, @NotNull BlockPos pos, @NotNull TreeConfiguration config) {
        // 放置树根基准方块
        setDirtAt(level, blockSetter, random, pos.below(), config);

        List<FoliagePlacer.FoliageAttachment> foliage = new ArrayList<>();
        BlockPos currentPos = pos;
        int currentHeight = this.baseHeight; // 初始高度
        int totalStages = random.nextInt(2, this.stages + 1); // 随机段
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);

        for (int i = 0; i < totalStages; i++) {
            currentHeight = Math.max(1, currentHeight - random.nextInt(1, 3));
            // 主干
            for (int y = 0; y < currentHeight; y++) {
                this.placeLog(level, blockSetter, random, currentPos.above(y), config);
            }
            currentPos = currentPos.above(currentHeight);

            // 放置分支
            if (i < this.stages - 1) {
                BlockPos branchPos = currentPos.relative(direction);
                this.placeBranch(level, blockSetter, random, branchPos, direction);
                foliage.add(new FoliagePlacer.FoliageAttachment(branchPos, 0, false));
            } else {
                //放置顶部的多个分枝和树叶
                List<Direction> horizontalDirs = new ArrayList<>(Direction.Plane.HORIZONTAL.stream().toList());
                Collections.shuffle(horizontalDirs);
                for(int j =0; j < random.nextInt(4); j++){
                    Direction branchDir = horizontalDirs.get(j);
                    BlockPos branchPos =
                }
            }
            direction = direction.getClockWise(); //用于下一次分支
        }
        return foliage;
    }

    // 自定义放置分支的方法，支持处理木头方向
    private void placeBranch(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, Direction direction) {
        BlockState state = this.branchProvider.getState(random, pos);
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            state = state.setValue(BlockStateProperties.HORIZONTAL_FACING, direction);
        }
        blockSetter.accept(pos, state);
    }
}
