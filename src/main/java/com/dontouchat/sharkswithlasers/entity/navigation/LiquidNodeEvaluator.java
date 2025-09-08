package com.dontouchat.sharkswithlasers.entity.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import org.jetbrains.annotations.Nullable;

public class LiquidNodeEvaluator extends SwimNodeEvaluator {
    public LiquidNodeEvaluator(boolean pAllowBreaching) {
        super(pAllowBreaching);
    }

    @Override
    protected @Nullable Node findAcceptedNode(int pX, int pY, int pZ) {
        Node node = null;
        BlockPathTypes blockpathtypes = this.getCachedBlockType(pX, pY, pZ);
        if (blockpathtypes == BlockPathTypes.BREACH || blockpathtypes == BlockPathTypes.WATER || blockpathtypes == BlockPathTypes.LAVA) {
            float f = this.mob.getPathfindingMalus(blockpathtypes);
            if (f >= 0.0F) {
                node = this.getNode(pX, pY, pZ);
                node.type = blockpathtypes;
                node.costMalus = Math.max(node.costMalus, f);
                if (this.level.getFluidState(new BlockPos(pX, pY, pZ)).isEmpty()) {
                    node.costMalus += 8.0F;
                }
            }
        }

        return node;
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter pLevel, int pX, int pY, int pZ, Mob pMob) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int i = pX; i < pX + this.entityWidth; ++i) {
            for (int j = pY; j < pY + this.entityHeight; ++j) {
                for (int k = pZ; k < pZ + this.entityDepth; ++k) {
                    BlockPos pos = blockpos$mutableblockpos.set(i, j, k);
                    FluidState fluidstate = pLevel.getFluidState(pos);
                    BlockState blockstate = pLevel.getBlockState(pos);
                    if (fluidstate.isEmpty() &&
                            (pLevel.getFluidState(pos).is(FluidTags.WATER) ||
                             pLevel.getFluidState(pos).is(FluidTags.LAVA)) &&
                            blockstate.isAir()) {
                        return BlockPathTypes.BREACH;
                    }

                    if (!fluidstate.is(FluidTags.WATER) || !fluidstate.is(FluidTags.LAVA)) {
                        return BlockPathTypes.BLOCKED;
                    }
                }
            }
        }

        BlockPos pos = blockpos$mutableblockpos;
        return (pLevel.getFluidState(pos).is(FluidTags.WATER) || pLevel.getFluidState(pos).is(FluidTags.LAVA)) ? BlockPathTypes.WATER : BlockPathTypes.BLOCKED;
    }
}
