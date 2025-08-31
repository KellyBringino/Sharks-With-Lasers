package com.dontouchat.sharkswithlasers.block.custom;

import com.dontouchat.sharkswithlasers.block.entity.ModBlockEntities;
import com.dontouchat.sharkswithlasers.block.entity.SifterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class SifterBlock extends BaseEntityBlock {

    public SifterBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock())
        {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof SifterBlockEntity)
            {
                ((SifterBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide())
        {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof SifterBlockEntity)
            {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer),(SifterBlockEntity)entity,pPos);
            }else{
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide())
        {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.SIFTER_BLOCK_ENTITY.get(),
                (pLevel1, pPos, pState1, pBlockEntity) ->
                        pBlockEntity.tick(pLevel1,pPos,pState1));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SifterBlockEntity(pPos, pState);
    }
}
