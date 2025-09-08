package com.dontouchat.sharkswithlasers.entity.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;

public class LiquidBoundPathNavigation extends WaterBoundPathNavigation {
    public LiquidBoundPathNavigation(Mob pMob, Level pLevel) {
        super(pMob, pLevel);
    }

    @Override
    protected PathFinder createPathFinder(int pMaxVisitedNodes) {
        this.nodeEvaluator = new LiquidNodeEvaluator(false);
        return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
    }
}
