package com.dontouchat.sharkswithlasers.entity.custom;

import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class SharkEntity extends Monster {
    protected final WaterBoundPathNavigation waterNavigation;
    public SharkEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new SharkMoveControl(this);
        this.waterNavigation = new WaterBoundPathNavigation(this, pLevel);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new SharkAttackGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(4,new SharkSwimGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,20)
                .add(Attributes.MOVEMENT_SPEED, 1.5f)
                .add(Attributes.ATTACK_DAMAGE,2f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1f)
                .add(Attributes.FOLLOW_RANGE, 35.0f);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    protected void handleAirSupply(int pAirSupply) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(pAirSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }

    }

    public void updateSwimming() {
        if (!this.level().isClientSide) {
            if (this.isEffectiveAi() && this.isInWater()) {
                this.navigation = this.waterNavigation;
                this.setSwimming(true);
            }
        }
    }
    public void travel(Vec3 pTravelVector) {
        if (this.isControlledByLocalInstance() && this.isInWater()) {
            this.moveRelative(0.01F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(pTravelVector);
        }
    }

    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(i);
    }
    public MobType getMobType() {
        return MobType.WATER;
    }
    public boolean checkSpawnObstruction(LevelReader pLevel) {
        return pLevel.isUnobstructed(this);
    }
    public boolean isPushedByFluid() {
        return false;
    }

    protected boolean canRandomSwim() { return true; }

    static class SharkMoveControl extends MoveControl {
        private final SharkEntity shark;

        SharkMoveControl(SharkEntity pShark) {
            super(pShark);
            this.shark = pShark;
        }

        public void tick() {
            if (this.shark.isEyeInFluid(FluidTags.WATER)) {
                this.shark.setDeltaMovement(this.shark.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }
            if (this.operation == MoveControl.Operation.MOVE_TO && !this.shark.getNavigation().isDone()) {
                float f = (float)(this.speedModifier * this.shark.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.shark.setSpeed(Mth.lerp(0.125F, this.shark.getSpeed(), f));
                double d0 = this.wantedX - this.shark.getX();
                double d1 = this.wantedY - this.shark.getY();
                double d2 = this.wantedZ - this.shark.getZ();
                if (d1 != 0.0D) {
                    double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    this.shark.setDeltaMovement(this.shark.getDeltaMovement().add(0.0D, (double)this.shark.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
                }
                if (d0 != 0.0D || d2 != 0.0D) {
                    float f1 = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.shark.setYRot(this.rotlerp(this.shark.getYRot(), f1, 90.0F));
                    this.shark.yBodyRot = this.shark.getYRot();
                }
            } else {
                this.shark.setSpeed(0.0F);
            }
        }
    }

    static class SharkSwimGoal extends RandomSwimmingGoal {
        private final SharkEntity fish;

        public SharkSwimGoal(SharkEntity pFish) {
            super(pFish, 1.0D, 5);
            this.fish = pFish;
        }
        public boolean canUse() {
            return this.fish.canRandomSwim() && super.canUse();
        }
    }

    public class SharkAttackGoal extends MeleeAttackGoal {
        private final SharkEntity shark;

        public SharkAttackGoal(SharkEntity pShark) {
            super(pShark, 2.0D, true);
            this.shark = pShark;
        }

        @Override
        protected double getAttackReachSqr(LivingEntity pAttackTarget) {
            return (double)(this.mob.getBbWidth() * 2.0F + pAttackTarget.getBbWidth());
        }

        public void stop() {
            super.stop();
            this.shark.setAggressive(false);
        }
        public void tick() {
            super.tick();
            if (this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
                this.shark.setAggressive(true);
            } else {
                this.shark.setAggressive(false);
            }
        }
    }
}
