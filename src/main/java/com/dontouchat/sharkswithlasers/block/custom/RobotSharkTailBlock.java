package com.dontouchat.sharkswithlasers.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RobotSharkTailBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape X_SHAPE = Block.box(3.0d,0.0d,0.0d,13.0d,8.0d,13.0d);
    public static final VoxelShape Z_SHAPE = Block.box(0.0d,0.0d,3.0d,13.0d,8.0d,13.0d);
    public static final VoxelShape X_NEG_SHAPE = Block.box(3.0d,0.0d,3.0d,13.0d,8.0d,16.0d);
    public static final VoxelShape Z_NEG_SHAPE = Block.box(3.0d,0.0d,3.0d,16.0d,8.0d,13.0d);
    public RobotSharkTailBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> X_SHAPE;
            case SOUTH -> X_NEG_SHAPE;
            case WEST -> Z_SHAPE;
            default -> Z_NEG_SHAPE;
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
