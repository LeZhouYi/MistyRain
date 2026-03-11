package com.skily_leyu.misty_rain.data.helpers;

import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.core.Direction;

public class ModelHelpers {

    public static VariantProperties.Rotation getXRot(Direction direction) {
        return switch (direction) {
            case DOWN -> VariantProperties.Rotation.R180;
            case UP -> VariantProperties.Rotation.R0;
            // 水平方向（北南东西）通常需要将模型“放倒”
            default -> VariantProperties.Rotation.R90;
        };
    }

    public static VariantProperties.Rotation getYRot(Direction direction) {
        return switch (direction) {
            case SOUTH -> VariantProperties.Rotation.R180;
            case WEST -> VariantProperties.Rotation.R270;
            case EAST -> VariantProperties.Rotation.R90;
            default -> VariantProperties.Rotation.R0; // NORTH 默认为 0
        };
    }

}
