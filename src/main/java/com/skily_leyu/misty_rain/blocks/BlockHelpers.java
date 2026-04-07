package com.skily_leyu.misty_rain.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockHelpers {
    /*
    根据初始方向和当前方向，计算出旋转后的碰撞箱
     */
    public static VoxelShape rotateShape(Direction to, VoxelShape shape) {
        VoxelShape[] buffer = {Shapes.empty()};
        // 遍历原始形状中的每一个小方块进行坐标转换
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            VoxelShape rotatedBox = switch (to) {
                case NORTH -> Shapes.box(minX, minY, minZ, maxX, maxY, maxZ);
                case SOUTH -> Shapes.box(1 - maxX, minY, 1 - maxZ, 1 - minX, maxY, 1 - minZ);
                case WEST  -> Shapes.box(minZ, minY, 1 - maxX, maxZ, maxY, 1 - minX);
                case EAST  -> Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX);
                case UP    -> Shapes.box(minX, 1 - maxZ, minY, maxX, 1 - minZ, maxY);
                case DOWN  -> Shapes.box(minX, minZ, 1 - maxY, maxX, maxZ, 1 - minY);
            };
            buffer[0] = Shapes.or(buffer[0], rotatedBox);
        });
        return buffer[0];
    }
}
