package com.dontouchat.sharkswithlasers.block.custom;

import com.dontouchat.sharkswithlasers.client.render.RenderUtil;
import com.dontouchat.sharkswithlasers.item.custom.LaserModuleItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class LaserAlarmBlock extends BaseEntityBlock {
    public ArrayList<BlockPos> connections = new ArrayList<>();
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public LaserAlarmBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(LIT, false)
        );
    }
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT) && !pLevel.hasNeighborSignal(pPos)) {
            pLevel.setBlock(pPos, pState.cycle(LIT), 2);
            RenderUtil.drawLine(pPos.getCenter(),pPos.getCenter().add(new Vec3(10,10,10)),1.0f,0.0f,0.0f,1.0f);

//            for(BlockPos p : connections){
//                Vec3 from = pPos.getCenter();
//                Vec3 to = p.getCenter();
//                HitResult hit =  pLevel.clip(new ClipContext(from, to,
//                        ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null));
//
//                if (hit.getType() == HitResult.Type.BLOCK){
//
//                } else if(hit.getType() == HitResult.Type.ENTITY) {
//
//                }
//            }
        }
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            boolean flag = pState.getValue(LIT);
            if (flag != pLevel.hasNeighborSignal(pPos)) {
                if (flag) {
                    pLevel.scheduleTick(pPos, this, 4);
                } else {
                    pLevel.setBlock(pPos, pState.cycle(LIT), 2);
                }
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
    }
}
