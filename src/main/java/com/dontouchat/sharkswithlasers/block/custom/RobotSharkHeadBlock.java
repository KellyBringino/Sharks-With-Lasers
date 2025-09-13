package com.dontouchat.sharkswithlasers.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;

public class RobotSharkHeadBlock extends AbstractRobotSharkPart {
    public static final VoxelShape X_SHAPE = Block.box(2.5d,3.0d,2.0d,13.5d,9.0d,14.0d);
    public static final VoxelShape Z_SHAPE = Block.box(2.0d,3.0d,2.5d,14.0d,9.0d,13.5d);
    public RobotSharkHeadBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH, SOUTH -> X_SHAPE;
            default -> Z_SHAPE;
        };
    }
}
