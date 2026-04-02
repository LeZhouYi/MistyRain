package com.skily_leyu.misty_rain.data;

import net.minecraft.core.Direction;

public class ModelHelpers {

    public static int getXRot(Direction direction) {
        return direction == Direction.DOWN ? 180 : (direction.getAxis().isHorizontal() ? 90 : 0);
    }

    public static int getYRot(Direction direction) {
        return switch (direction) {
            case SOUTH -> 180;
            case EAST -> 90;
            case WEST -> 270;
            default -> 0;
        };
    }

}
