package com.dontouchat.sharkswithlasers.item.custom;

import com.dontouchat.sharkswithlasers.block.custom.AbstractRobotSharkPart;
import com.dontouchat.sharkswithlasers.block.custom.RobotSharkChassisBlock;
import com.dontouchat.sharkswithlasers.block.custom.RobotSharkHeadBlock;
import com.dontouchat.sharkswithlasers.block.custom.RobotSharkTailBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

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
            if (((AbstractRobotSharkPart) block).isConnected(pBlockState, pLevel, pBlockPos)) {
                ArrayList<BlockPos> pattern = ((AbstractRobotSharkPart) block).getConnected(pBlockState, pLevel, pBlockPos);
                for (BlockPos p : pattern) {
                    pContext.getPlayer().sendSystemMessage(Component.literal(pLevel.getBlockState(p).toString()));
                    pLevel.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                    pLevel.updateNeighborsAt(p, pLevel.getBlockState(p).getBlock());
                    pLevel.levelEvent(2001, p, Block.getId(pLevel.getBlockState(p)));
                }
                pContext.getPlayer().sendSystemMessage(Component.literal("connected"));
            } else {
                pContext.getPlayer().sendSystemMessage(Component.literal("not connected"));
            }
        }
        return InteractionResult.PASS;
    }
}
