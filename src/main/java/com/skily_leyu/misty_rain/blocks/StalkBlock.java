package com.skily_leyu.misty_rain.blocks;

import com.mojang.serialization.MapCodec;
import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class StalkBlock extends DirectionalBlock {
    public static final BooleanProperty NATURAL = BooleanProperty.create("natural");

    public static final MapCodec<BranchBlock> CODEC = simpleCodec(BranchBlock::new);

    protected static final VoxelShape Y_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    protected static final VoxelShape X_SHAPE = Block.box(0.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D);
    protected static final VoxelShape Z_SHAPE = Block.box(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 16.0D);

    public StalkBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
            .setValue(FACING, Direction.NORTH)
            .setValue(NATURAL, Boolean.FALSE));
    }

    @Override
    protected @NotNull MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return 0;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        // 根据 AXIS 属性返回对应的 VoxelShape
        return switch (state.getValue(FACING).getAxis()) {
            case X -> X_SHAPE;
            case Z -> Z_SHAPE;
            default -> Y_SHAPE;
        };
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(NATURAL) && state.getValue(FACING) == Direction.DOWN) {
            BlockState downBlockState = level.getBlockState(pos.below());
            if (!isSustainingBlock(downBlockState)) {
                if (level instanceof Level world && !world.isClientSide) {
                    world.destroyBlock(pos, true);
                }
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    /**
     * 判断是否是可以维持生长的方块
     */
    private boolean isSustainingBlock(@NotNull BlockState blockState) {
        return blockState.getBlock() == MistyRain.BAMBOO_STALK_BLOCK.get() && blockState.getValue(NATURAL) && blockState.getValue(FACING) == Direction.DOWN;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, NATURAL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
            .setValue(FACING, context.getClickedFace())
            .setValue(NATURAL, false);
    }

}
