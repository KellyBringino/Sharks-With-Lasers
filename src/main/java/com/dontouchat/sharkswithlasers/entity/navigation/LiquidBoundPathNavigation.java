package com.dontouchat.sharkswithlasers.entity.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class LiquidBoundPathNavigation extends WaterBoundPathNavigation {
    public LiquidBoundPathNavigation(Mob pMob, Level pLevel) {
        super(pMob, pLevel);
    }

    @Override
    protected PathFinder createPathFinder(int pMaxVisitedNodes) {
        this.nodeEvaluator = new LiquidNodeEvaluator(false);
        return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
    }

    @Override
    protected boolean canMoveDirectly(Vec3 pPosVec31, Vec3 pPosVec32) {
        return isClearForMovementBetween(this.mob, pPosVec31, pPosVec32, true);
    }
}
