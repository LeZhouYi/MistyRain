package com.skily_leyu.misty_rain.blocks;

import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BranchedLogBlock extends Block {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final BooleanProperty NATURAL = BooleanProperty.create("natural");
    public static final BooleanProperty BRANCHED = BooleanProperty.create("branched");

    public BranchedLogBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
            .setValue(FACING, Direction.UP)
            .setValue(NATURAL, Boolean.FALSE)
            .setValue(BRANCHED, Boolean.FALSE));
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level, @NotNull ScheduledTickAccess scheduledTickAccess, @NotNull BlockPos pos, @NotNull Direction direction, @NotNull BlockPos neighborPos, @NotNull BlockState neighborState, @NotNull RandomSource random) {
        if (state.getValue(NATURAL)) {
            BlockState downBlockState = level.getBlockState(pos.below());
            if (!isSustainingBlock(downBlockState)) {
                if (level instanceof Level world && !world.isClientSide) {
                    world.destroyBlock(pos, true);
                }
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    private boolean isSustainingBlock(@NotNull BlockState blockState) {
        /*判断是否是可以维持生长的方块*/
        return blockState.getBlock() == MistyRain.BAMBOO_STALK_BLOCK.get() &&
            blockState.getValue(NATURAL); //TODO:添加竹子基底
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, NATURAL, BRANCHED);
    }
}
