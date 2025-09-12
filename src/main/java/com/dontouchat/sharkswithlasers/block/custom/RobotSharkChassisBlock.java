package com.dontouchat.sharkswithlasers.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RobotSharkChassisBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape X_SHAPE = Block.box(2.0d,0.0d,-2.0d,14.0d,10.0d,18.0d);
    public static final VoxelShape Z_SHAPE = Block.box(-2.0d,0.0d,2.0d,18.0d,10.0d,14.0d);
    public RobotSharkChassisBlock(BlockBehaviour.Properties pProperties) {
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

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
