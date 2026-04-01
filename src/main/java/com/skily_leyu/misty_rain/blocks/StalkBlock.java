package com.skily_leyu.misty_rain.blocks;

import com.google.common.collect.ImmutableMap;
import com.skily_leyu.misty_rain.MistyRain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Collectors;

public class StalkBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final EnumProperty<Direction> ROTATE = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty NATURAL = BooleanProperty.create("natural");
    public static final BooleanProperty BRANCHED = BooleanProperty.create("branched");

    public static final VoxelShape STALK = Block.box(3, 0, 3, 13, 16, 13);
    private final Map<BlockState, VoxelShape> shapesCache;

    public StalkBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
            .setValue(AXIS, Direction.DOWN.getAxis())
            .setValue(ROTATE, Direction.EAST)
            .setValue(NATURAL, Boolean.FALSE)
            .setValue(BRANCHED, Boolean.FALSE));
        this.shapesCache = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream()
            .collect(Collectors.toMap(state -> state, this::calculateShape)));
    }

    /**
     * 将基于 North 的 VoxelShape 旋转到指定方向
     *
     * @param source    基于 North 的原始形状
     * @param direction 目标朝向
     * @return 旋转后的形状
     */
    public static VoxelShape rotateShape(VoxelShape source, Direction direction) {
        VoxelShape[] buffer = new VoxelShape[]{source, Shapes.empty()};

        // 根据方向旋转（North 为 0 度，不需要处理）
        int times = (direction.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;

        for (int i = 0; i < times; i++) {
            buffer[1] = Shapes.empty();
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                // 顺时针 90 度旋转公式: (x, z) -> (1-z, x)
                buffer[1] = Shapes.or(buffer[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX));
            });
            buffer[0] = buffer[1];
        }
        return buffer[0];
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return this.shapesCache.get(state);
    }

    private VoxelShape calculateShape(BlockState state) {
        Direction.Axis facing = state.getValue(AXIS);
        return rotateShape(STALK, facing);
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(NATURAL)) {
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
        return blockState.getBlock() == MistyRain.BAMBOO_STALK_BLOCK.get() &&
            //TODO:添加竹子基底
            blockState.getValue(NATURAL);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, ROTATE, NATURAL, BRANCHED);
    }
}
