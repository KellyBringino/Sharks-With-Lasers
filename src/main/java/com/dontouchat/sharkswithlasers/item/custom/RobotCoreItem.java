package com.dontouchat.sharkswithlasers.item.custom;

import com.dontouchat.sharkswithlasers.block.ModBlocks;
import com.dontouchat.sharkswithlasers.block.custom.AbstractRobotSharkPart;
import com.dontouchat.sharkswithlasers.entity.ModEntities;
import com.dontouchat.sharkswithlasers.entity.custom.RobotSharkEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

import java.util.ArrayList;

public class RobotCoreItem extends Item {
    public RobotCoreItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        BlockPos pBlockPos = pContext.getClickedPos();
        BlockState pBlockState = pLevel.getBlockState(pBlockPos);
        Block block = pBlockState.getBlock();
        if(block instanceof AbstractRobotSharkPart){
            BlockPattern.BlockPatternMatch robotsharkpattern =
                BlockPatternBuilder.start()
                .aisle("HCT")
                .where('H', BlockInWorld
                        .hasState(BlockStatePredicate.forBlock(ModBlocks.ROBOT_SHARK_HEAD_BLOCK.get())))
                .where('C', BlockInWorld
                        .hasState(BlockStatePredicate.forBlock(ModBlocks.ROBOT_SHARK_CHASSIS_BLOCK.get())))
                .where('T', BlockInWorld
                        .hasState(BlockStatePredicate.forBlock(ModBlocks.ROBOT_SHARK_TAIL_BLOCK.get())))
                    .build().find(pLevel,pBlockPos);
            BlockPattern.BlockPatternMatch reverserobotsharkpattern =
                    BlockPatternBuilder.start()
                            .aisle("TCH")
                            .where('H', BlockInWorld
                                    .hasState(BlockStatePredicate.forBlock(ModBlocks.ROBOT_SHARK_HEAD_BLOCK.get())))
                            .where('C', BlockInWorld
                                    .hasState(BlockStatePredicate.forBlock(ModBlocks.ROBOT_SHARK_CHASSIS_BLOCK.get())))
                            .where('T', BlockInWorld
                                    .hasState(BlockStatePredicate.forBlock(ModBlocks.ROBOT_SHARK_TAIL_BLOCK.get())))
                            .build().find(pLevel,pBlockPos);
            if(robotsharkpattern != null || reverserobotsharkpattern != null){
                RobotSharkEntity robotshark = ModEntities.ROBOT_SHARK.get().create(pLevel);
                if(robotshark != null){
                    spawnSharkInWorld(pLevel,robotsharkpattern != null ? robotsharkpattern:reverserobotsharkpattern,robotshark,pBlockPos);
                }
            }

            if (!pContext.getPlayer().getAbilities().instabuild) {
                pContext.getPlayer().getItemInHand(pContext.getHand()).shrink(1);
            }
        }
        return InteractionResult.PASS;
    }

    private static void spawnSharkInWorld(Level pLevel, BlockPattern.BlockPatternMatch pPatternMatch, Entity pShark, BlockPos pPos) {
        clearPatternBlocks(pLevel, pPatternMatch);
        pShark.moveTo((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.05D, (double)pPos.getZ() + 0.5D, 0.0F, 0.0F);
        pLevel.addFreshEntity(pShark);

        for(ServerPlayer serverplayer : pLevel.getEntitiesOfClass(ServerPlayer.class, pShark.getBoundingBox().inflate(5.0D))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, pShark);
        }

        updatePatternBlocks(pLevel, pPatternMatch);
    }

    public static void clearPatternBlocks(Level pLevel, BlockPattern.BlockPatternMatch pPatternMatch) {
        for(int i = 0; i < pPatternMatch.getWidth(); ++i) {
            for(int j = 0; j < pPatternMatch.getHeight(); ++j) {
                BlockInWorld blockinworld = pPatternMatch.getBlock(i, j, 0);
                BlockState oldState = pLevel.getBlockState(blockinworld.getPos());
                pLevel.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                pLevel.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
                pLevel.sendBlockUpdated(blockinworld.getPos(),oldState,Blocks.AIR.defaultBlockState(),7);
            }
        }
    }
    public static void updatePatternBlocks(Level pLevel, BlockPattern.BlockPatternMatch pPatternMatch) {
        for(int i = 0; i < pPatternMatch.getWidth(); ++i) {
            for(int j = 0; j < pPatternMatch.getHeight(); ++j) {
                BlockInWorld blockinworld = pPatternMatch.getBlock(i, j, 0);
                pLevel.blockUpdated(blockinworld.getPos(), Blocks.AIR);
            }
        }
    }
}
