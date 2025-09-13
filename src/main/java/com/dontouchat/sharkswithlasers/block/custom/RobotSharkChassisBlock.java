package com.dontouchat.sharkswithlasers.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RobotSharkChassisBlock extends AbstractRobotSharkPart {
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
    public ArrayList<BlockPos> getConnected(BlockState pState, Level pLevel, BlockPos pPos){
        Block facingBlock = pLevel.getBlockState(pPos.relative(pState.getValue(FACING))).getBlock();
        Block oppositeBlock = pLevel.getBlockState(pPos.relative(pState.getValue(FACING).getOpposite())).getBlock();
        pLevel.updateNeighborsAt(pPos,this);
        if(oppositeBlock instanceof RobotSharkTailBlock &&
                facingBlock instanceof RobotSharkHeadBlock){
            ArrayList result = new ArrayList<>();
            result.add(pPos);
            result.add(pPos.relative(pState.getValue(FACING)));
            result.add(pPos.relative(pState.getValue(FACING).getOpposite()));
            return result;
        }
        else return new ArrayList<>();
    }

    @Override
    public boolean isConnected(BlockState pState, Level pLevel, BlockPos pPos){
        Block facingBlock = pLevel.getBlockState(pPos.relative(pState.getValue(FACING))).getBlock();
        Block oppositeBlock = pLevel.getBlockState(pPos.relative(pState.getValue(FACING).getOpposite())).getBlock();
        return oppositeBlock instanceof RobotSharkTailBlock &&
                facingBlock instanceof RobotSharkHeadBlock;
    }

    @Override
    public void breakConnected(BlockState pState, Level pLevel, BlockPos pPos){
        BlockPos oppositePos = pPos.relative(pState.getValue(FACING).getOpposite());
        BlockState oppositeState = pLevel.getBlockState(oppositePos);
        Block oppositeBlock = oppositeState.getBlock();
        BlockPos facingPos = pPos.relative(pState.getValue(FACING));
        BlockState facingState = pLevel.getBlockState(facingPos);
        Block facingBlock = facingState.getBlock();
        if(oppositeBlock instanceof RobotSharkTailBlock
            && facingBlock instanceof RobotSharkTailBlock){
            pLevel.setBlockAndUpdate(oppositePos,Blocks.AIR.defaultBlockState());
            pLevel.setBlockAndUpdate(facingPos,Blocks.AIR.defaultBlockState());
        }
    }
    public void breakTail(BlockState pState, Level pLevel, BlockPos pPos){
        BlockPos oppositePos = pPos.relative(pState.getValue(FACING).getOpposite());
        BlockState oppositeState = pLevel.getBlockState(oppositePos);
        Block oppositeBlock = oppositeState.getBlock();
        if(oppositeBlock instanceof RobotSharkTailBlock){
            pLevel.setBlockAndUpdate(oppositePos,Blocks.AIR.defaultBlockState());
        }
    }
    public void breakHead(BlockState pState, Level pLevel, BlockPos pPos){
        BlockPos facingPos = pPos.relative(pState.getValue(FACING));
        BlockState facingState = pLevel.getBlockState(facingPos);
        Block facingBlock = facingState.getBlock();
        if(facingBlock instanceof RobotSharkTailBlock){
            pLevel.setBlockAndUpdate(facingPos,Blocks.AIR.defaultBlockState());
        }
    }
}
