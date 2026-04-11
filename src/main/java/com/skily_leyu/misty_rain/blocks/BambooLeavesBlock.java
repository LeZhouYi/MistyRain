package com.skily_leyu.misty_rain.blocks;

import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class BambooLeavesBlock extends Block {
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;

    public BambooLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
            .setValue(PERSISTENT, Boolean.TRUE)
            .setValue(DISTANCE, 7));
    }

    private static int getDistanceAt(BlockState neighbor) {
        if (neighbor.is(MistyRain.BAMBOO_STALK_BLOCK.get()) && !neighbor.getValue(PERSISTENT)) {
            return 1;
        } else if (neighbor.is(MistyRain.BAMBOO_LEAVES_BLOCK.get()) && !neighbor.getValue(PERSISTENT)) {
            //最大值的限制在被调用的方法实现
            return neighbor.getValue(DISTANCE) + 1;
        }
        return 7;
    }

    private static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
        int distance = 7;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Direction dir : Direction.values()) {
            mutablePos.setWithOffset(pos, dir);
            distance = Math.min(distance, getDistanceAt(level.getBlockState(mutablePos)));
        }
        return state.setValue(DISTANCE, distance);
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (!state.getValue(PERSISTENT)) {
            int distance = getDistanceAt(neighborState);
            if (distance == 1 || state.getValue(DISTANCE) != distance) {
                // 如果周围有变化，计划一个刻来重新计算整个方块的距离
                level.scheduleTick(pos, this, 1);
            }
        }
        return state;
    }

    @Override
    public void randomTick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        // 只有非人工放置且距离过远才凋零
        if (!state.getValue(PERSISTENT) && state.getValue(DISTANCE) == 7) {
            dropResources(state, level, pos); // 掉落资源
            level.removeBlock(pos, false);
        }
    }

    @Override
    public void tick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        level.setBlock(pos, updateDistance(state, level, pos), 3);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PERSISTENT, DISTANCE);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(DISTANCE) == 7 && !state.getValue(PERSISTENT);
    }

}
