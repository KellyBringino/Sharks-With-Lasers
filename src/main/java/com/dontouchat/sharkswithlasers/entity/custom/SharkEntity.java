package com.dontouchat.sharkswithlasers.entity.custom;

import com.dontouchat.sharkswithlasers.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SharkEntity extends Monster {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(SharkEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int attackAnimationTimeout = 0;
    private int idleAnimationTimeout = 0;
    protected final WaterBoundPathNavigation waterNavigation;

    public SharkEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new SharkMoveControl(this);
        this.waterNavigation = new WaterBoundPathNavigation(this, pLevel);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide())
        {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates()
    {
        if(this.idleAnimationTimeout <= 0)
        {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }

        if(this.isAttacking() && attackAnimationTimeout <= 0){
            attackAnimationTimeout = 10;
            attackAnimationState.start(this.tickCount);
        }else{
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()){
            attackAnimationState.stop();
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose() == Pose.STANDING){
            f = Math.min(pPartialTick * 6F, 1f);
        }else{
            f = 0f;
        }
        this.walkAnimation.update(f,0.2f);
    }

    public void setAttacking(boolean attacking){
        this.entityData.set(ATTACKING, attacking);
    }
    public boolean isAttacking(){
        return this.entityData.get(ATTACKING);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING,false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new SharkAttackGoal(this));
        this.goalSelector.addGoal(4,new SharkSwimGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4,new LookAtPlayerGoal(this,Player.class,8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3,new NearestAttackableTargetGoal<>(this, WaterAnimal.class,true));
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,20)
                .add(Attributes.MOVEMENT_SPEED, 3f)
                .add(Attributes.ATTACK_DAMAGE,2f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1f)
                .add(Attributes.FOLLOW_RANGE, 35.0f);
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
            if (this.isEffectiveAi() && this.isTouchingSwimable()) {
                this.navigation = this.waterNavigation;
                this.setSwimming(true);
            }
        }
    }
    public void travel(Vec3 pTravelVector) {
        if (this.isControlledByLocalInstance() && this.isTouchingSwimable()) {
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
        if(this.getItemBySlot(EquipmentSlot.MAINHAND).is(ModItems.LASER_COLLAR.get())){
            this.getItemBySlot(EquipmentSlot.MAINHAND)
                    .inventoryTick(level(),this,0,true);
        }
    }
    public boolean checkSpawnObstruction(LevelReader pLevel) {
        return pLevel.isUnobstructed(this);
    }
    public MobType getMobType() {
        return MobType.WATER;
    }
    public boolean canBreatheUnderwater() {
        return true;
    }
    public boolean isPushedByFluid() {
        return false;
    }
    public boolean isEyeInSwimable(){return this.isEyeInFluid(FluidTags.WATER);}
    public boolean isTouchingSwimable(){return this.isInWater();}
    @Override
    public boolean canHoldItem(ItemStack pStack) {
        return super.canHoldItem(pStack);
    }
    public boolean equipLaser(ItemStack pStack, Player pPlayer){
        if(!getItemBySlot(EquipmentSlot.MAINHAND).is(ModItems.LASER_COLLAR.get()) && pStack.is(ModItems.LASER_COLLAR.get()))
        {
            setItemInHand(InteractionHand.MAIN_HAND,pStack);
            return true;
        }
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
            if (this.shark.isEyeInSwimable()) {
                this.shark.setDeltaMovement(this.shark.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }
            else if(this.shark.isTouchingSwimable()){
                this.shark.setDeltaMovement(this.shark.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
            if (this.operation == MoveControl.Operation.MOVE_TO && !this.shark.getNavigation().isDone() && this.shark.isEyeInSwimable()) {
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
        protected final SharkEntity shark;

        public SharkSwimGoal(SharkEntity pFish) {
            super(pFish, 1.0D, 5);
            this.shark = pFish;
        }
        @Override
        @Nullable
        protected Vec3 getPosition() {
            return getRandomSwimmablePos(this.mob, 10, 7);
        }

        @Nullable
        public Vec3 getRandomSwimmablePos(PathfinderMob pPathfinder, int pRadius, int pVerticalDistance) {
            Vec3 vec3 = DefaultRandomPos.getPos(pPathfinder, pRadius, pVerticalDistance);

            for(int i = 0;
                vec3 != null &&
                        !this.shark.level().getFluidState(BlockPos.containing(vec3)).is(FluidTags.WATER) &&
                        i++ < 10;
                vec3 = DefaultRandomPos.getPos(pPathfinder, pRadius, pVerticalDistance))
            {}

            return vec3;
        }

        public boolean canUse() {
            return this.shark.canRandomSwim() && super.canUse();
        }
    }

    public class SharkAttackGoal extends MeleeAttackGoal {
        private final SharkEntity shark;
        private int attackDelay = 2;
        private int ticksUntilNextAttack = 8;
        private boolean shouldCountTillNextAttack = false;

        public SharkAttackGoal(SharkEntity pShark) {
            super(pShark, 2.0D, true);
            this.shark = pShark;
        }

        @Override
        protected double getAttackReachSqr(LivingEntity pAttackTarget) {
            return (double)(this.mob.getBbWidth() * 3.0F + pAttackTarget.getBbWidth());
        }
        @Override
        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
            double dist = this.getAttackReachSqr(pEnemy);
            if (pDistToEnemySqr <= dist) {
                shouldCountTillNextAttack = true;

                if(isTimeToStartAttackAnimation()) {
                    shark.setAttacking(true);
                }

                if(isTimeToAttack()) {
                    this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                    performAttack(pEnemy);
                }
            } else {
                resetAttackCooldown();
                shouldCountTillNextAttack = false;
                shark.setAttacking(false);
                shark.attackAnimationTimeout = 0;
            }
        }
        protected boolean isTimeToStartAttackAnimation() {
            return this.ticksUntilNextAttack <= attackDelay;
        }
        protected void performAttack(LivingEntity pEnemy) {
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(pEnemy);
        }

        @Override
        public void start(){
            super.start();
            attackDelay = 2;
            ticksUntilNextAttack = 8;
        }
        @Override
        public void stop() {
            super.stop();
            this.shark.setAttacking(false);
            this.shark.setAggressive(false);
        }
        @Override
        public void tick() {
            super.tick();
            if(shouldCountTillNextAttack){
                this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            }
        }
    }
}
