package com.dontouchat.sharkswithlasers.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;

public class RobotSharkTailBlock extends AbstractRobotSharkPart {
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
    public boolean isConnected(BlockState pState, Level pLevel, BlockPos pPos){
        BlockPos pBlockPos = pPos.relative(pState.getValue(FACING));
        BlockState pBlockState = pLevel.getBlockState(pBlockPos);
        Block pblock = pLevel.getBlockState(pBlockPos).getBlock();
        return pblock instanceof RobotSharkChassisBlock
                && ((RobotSharkChassisBlock)pblock).isConnected(pBlockState,pLevel,pBlockPos);
    }
    @Override
    public ArrayList<BlockPos> getConnected(BlockState pState, Level pLevel, BlockPos pPos){
        BlockPos chassisPos = pPos.relative(pState.getValue(FACING));
        BlockState chassisState = pLevel.getBlockState(chassisPos);
        Block pblock = pLevel.getBlockState(chassisPos).getBlock();
        if(pblock instanceof RobotSharkChassisBlock){
            return ((RobotSharkChassisBlock) pblock).getConnected(chassisState,pLevel,chassisPos);
        }
        else return new ArrayList<>();
    }

    @Override
    public void breakConnected(BlockState pState, Level pLevel, BlockPos pPos){
        BlockPos chassisPos = pPos.relative(pState.getValue(FACING));
        BlockState chassisState = pLevel.getBlockState(chassisPos);
        Block pblock = pLevel.getBlockState(chassisPos).getBlock();
        if(pblock instanceof RobotSharkChassisBlock){
            ((RobotSharkChassisBlock) pblock).breakHead(chassisState,pLevel,chassisPos);
            pLevel.setBlockAndUpdate(chassisPos, Blocks.AIR.defaultBlockState());
        }
    }
}
