package com.dontouchat.sharkswithlasers.entity.custom;

import com.dontouchat.sharkswithlasers.entity.ModEntities;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class LaserProjectileEntity extends AbstractArrow {
    private double baseDamage = 2.0D;
    private int knockback;
    private SoundEvent soundEvent =

    public LaserProjectileEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public LaserProjectileEntity(Level pLevel,LivingEntity livingEntity) {
        super(ModEntities.LASER.get(),livingEntity,pLevel);
        setKnockback(1);
        setNoGravity(true);
    }
    public void setKnockback(int pKnockback) {
        this.knockback = pKnockback;
    }

    public int getKnockback() {
        return this.knockback;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.discard();
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity hitEntity = pResult.getEntity();
        float f = (float)this.getDeltaMovement().length();
        int i = Mth.ceil(Mth.clamp((double)f * this.baseDamage, 0.0D, (double)Integer.MAX_VALUE));

        Entity ownerEntity = this.getOwner();
        DamageSource damagesource;
        if (ownerEntity == null) {
            damagesource = this.damageSources().generic();
        } else {
            damagesource = this.damageSources().mobProjectile(this, (LivingEntity) ownerEntity);
            if (ownerEntity instanceof LivingEntity) {
                ((LivingEntity)ownerEntity).setLastHurtMob(hitEntity);
            }
        }

        boolean enderFlag = hitEntity.getType() == EntityType.ENDERMAN;
        int fireTicks = hitEntity.getRemainingFireTicks();
        if (this.isOnFire() && !enderFlag) {
            hitEntity.setSecondsOnFire(5);
        }

        if (hitEntity.hurt(damagesource, (float)i)) {
            if (enderFlag) {
                return;
            }

            if (hitEntity instanceof LivingEntity) {
                LivingEntity livingHitEntity = (LivingEntity)hitEntity;

                if (this.knockback > 0) {
                    double d0 = Math.max(0.0D, 1.0D - livingHitEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D * d0);
                    if (vec3.lengthSqr() > 0.0D) {
                        livingHitEntity.push(vec3.x, 0.1D, vec3.z);
                    }
                }

                if (ownerEntity != null && livingHitEntity != ownerEntity && livingHitEntity instanceof Player && ownerEntity instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer)ownerEntity).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }
            }

            this.playSound(this.soundEvent, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.discard();
        } else {
            hitEntity.setRemainingFireTicks(fireTicks);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.setYRot(this.getYRot() + 180.0F);
            this.yRotO += 180.0F;
            if (!this.level().isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                this.discard();
            }
        }

    }
}
