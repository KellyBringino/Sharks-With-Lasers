package com.dontouchat.sharkswithlasers.entity.custom;

import com.dontouchat.sharkswithlasers.entity.navigation.LiquidBoundPathNavigation;
import com.dontouchat.sharkswithlasers.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RobotSharkEntity extends SharkEntity{
    private final SoundEvent chompSound = ModSounds.ROBOT_SHARK_CHOMP.get();
    private final SoundEvent sharkSound = ModSounds.ROBOT_SHARK_SHARK.get();
    private final SoundEvent ouchSound = ModSounds.ROBOT_SHARK_OUCH.get();
    private final SoundEvent deathSound = ModSounds.ROBOT_SHARK_DEATH.get();
    protected final LiquidBoundPathNavigation liquidNavigation;
    public RobotSharkEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.liquidNavigation = new LiquidBoundPathNavigation(this, pLevel);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new TryFindWaterOrLavaGoal(this));
        this.goalSelector.addGoal(1, new SharkAttackGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3,new NearestAttackableTargetGoal<>(this, WaterAnimal.class,true));
        this.goalSelector.addGoal(4,new RobotSharkSwimGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public void updateSwimming() {
        if (!this.level().isClientSide) {
            if (this.isEffectiveAi() && this.isTouchingSwimable()) {
                this.navigation = this.liquidNavigation;
                this.setSwimming(true);
            }
        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,40)
                .add(Attributes.MOVEMENT_SPEED, 4f)
                .add(Attributes.ATTACK_DAMAGE,4f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1f)
                .add(Attributes.FOLLOW_RANGE, 35.0f);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {return level().getRandom().nextBoolean() ? sharkSound : chompSound;}
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {return ouchSound;}
    @Override
    public SoundEvent getDeathSound() {return deathSound;}

    @Override
    protected void handleAirSupply(int pAirSupply) {}
    @Override
    public boolean isEyeInSwimable(){return this.isEyeInFluid(FluidTags.WATER) || this.isEyeInFluid(FluidTags.LAVA);}
    @Override
    public boolean isTouchingSwimable(){return this.isInWaterOrBubble() || this.isInLava();}

    static class TryFindWaterOrLavaGoal extends Goal {
        private final PathfinderMob mob;

        public TryFindWaterOrLavaGoal(PathfinderMob pMob) {
            this.mob = pMob;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return this.mob.onGround() &&
                    (!this.mob.level().getFluidState(this.mob.blockPosition()).is(FluidTags.WATER) ||
                    !this.mob.level().getFluidState(this.mob.blockPosition()).is(FluidTags.LAVA));
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            BlockPos blockpos = null;

            for(BlockPos blockpos1 : BlockPos.betweenClosed(Mth.floor(this.mob.getX() - 2.0D), Mth.floor(this.mob.getY() - 2.0D), Mth.floor(this.mob.getZ() - 2.0D), Mth.floor(this.mob.getX() + 2.0D), this.mob.getBlockY(), Mth.floor(this.mob.getZ() + 2.0D))) {
                if (this.mob.level().getFluidState(blockpos1).is(FluidTags.WATER) || this.mob.level().getFluidState(blockpos1).is(FluidTags.LAVA)) {
                    blockpos = blockpos1;
                    break;
                }
            }

            if (blockpos != null) {
                this.mob.getMoveControl().setWantedPosition((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), 1.0D);
            }

        }
    }

    static class RobotSharkSwimGoal extends SharkSwimGoal{

        public RobotSharkSwimGoal(SharkEntity pFish) {
            super(pFish);
        }

        @Override
        public @Nullable Vec3 getRandomSwimmablePos(PathfinderMob pPathfinder, int pRadius, int pVerticalDistance) {
            Vec3 vec3 = DefaultRandomPos.getPos(pPathfinder, pRadius, pVerticalDistance);

            for(int i = 0;
                vec3 != null &&
                        (!this.shark.level().getFluidState(BlockPos.containing(vec3)).is(FluidTags.WATER)||
                                !this.shark.level().getFluidState(BlockPos.containing(vec3)).is(FluidTags.LAVA))&&
                        i++ < 10;
                vec3 = DefaultRandomPos.getPos(pPathfinder, pRadius, pVerticalDistance))
            {}

            return vec3;
        }
    }
}
