package com.skily_leyu.misty_rain.blocks;

import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import com.skily_leyu.misty_rain.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BambooBranchBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    public static final BooleanProperty EXTENDED = BlockStateProperties.EXTENDED;

    public static final MapCodec<BambooBranchBlock> CODEC = simpleCodec(BambooBranchBlock::new);

    private static final VoxelShape BASE_SHAPE = Block.box(7.0D, 1.5D, 4.9D, 13.1D, 8.0D, 16.D);
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(Direction.class);

    static {
        for (Direction dir : Direction.values()) {
            SHAPES.put(dir, BlockHelpers.rotateShape(dir, BASE_SHAPE));
        }
    }

    public BambooBranchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
            .setValue(FACING, Direction.NORTH)
            .setValue(PERSISTENT, Boolean.TRUE)
            .setValue(EXTENDED, Boolean.FALSE));
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        BlockPos neighborPos = pos.relative(clickedFace.getOpposite());

        BlockState neighborState = level.getBlockState(neighborPos);
        boolean shouldExtend = false;
        if (neighborState.is(ModBlocks.BAMBOO_STALK_BLOCK.get())) {
            if (neighborState.hasProperty(BlockStateProperties.FACING)) {
                Direction neighborFacing = neighborState.getValue(BlockStateProperties.FACING);
                if (neighborFacing.getAxis() != clickedFace.getAxis()) {
                    shouldExtend = true;
                }
            }
        }
        return this.defaultBlockState()
            .setValue(FACING, context.getClickedFace())
            .setValue(EXTENDED, shouldExtend);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, PERSISTENT, EXTENDED);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        Direction facing = state.getValue(FACING);
        BlockState oppositeBlockState = level.getBlockState(pos.relative(facing));
        if (!state.getValue(PERSISTENT)) {
            if (!canSurviveOn(oppositeBlockState)) {
                if (level instanceof Level world && !world.isClientSide) {
                    world.destroyBlock(pos, true);
                }
                return Blocks.AIR.defaultBlockState();
            }
        }
        boolean shouldExtend = false;
        if (neighborState.is(ModBlocks.BAMBOO_STALK_BLOCK.get())) {
            if (neighborState.hasProperty(BlockStateProperties.FACING)) {
                Direction neighborFacing = neighborState.getValue(BlockStateProperties.FACING);
                if (neighborFacing.getAxis() != facing.getAxis()) {
                    shouldExtend = true;
                }
            }
        }
        state.setValue(EXTENDED, shouldExtend);
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    /**
     * 判断是否是可以维持生长的方块
     */
    private boolean canSurviveOn(@NotNull BlockState blockState) {
        return blockState.getBlock() == ModBlocks.BAMBOO_STALK_BLOCK.get()
            && !blockState.getValue(BambooStalkBlock.PERSISTENT)
            && blockState.getValue(DirectionalBlock.FACING) == Direction.UP;
    }
}
