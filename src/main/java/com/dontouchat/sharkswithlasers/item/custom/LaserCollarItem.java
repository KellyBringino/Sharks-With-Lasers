package com.dontouchat.sharkswithlasers.item.custom;

import com.dontouchat.sharkswithlasers.entity.custom.LaserProjectileEntity;
import com.dontouchat.sharkswithlasers.entity.custom.SharkEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DropperBlock;

public class LaserCollarItem extends Item {
    private int countTilNextLaser;
    public LaserCollarItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer,
                        LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if(pInteractionTarget instanceof SharkEntity){
            SharkEntity shark = (SharkEntity) pInteractionTarget;
            boolean b = shark.equipLaser(pStack.copy(),pPlayer);
            if(b){
                pStack.setCount(0);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId,
                              boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if(pEntity instanceof SharkEntity)
        {
            pEntity.sendSystemMessage(Component.literal("sharkequiped"));
            if(countTilNextLaser > 0)
            {
                --countTilNextLaser;
            }else{
                shoot(pLevel, pEntity);
                countTilNextLaser = pLevel.getRandom().nextInt(100)+100;
            }
        }
    }

    private void shoot(Level pLevel, Entity pEntity)
    {
        LaserProjectileEntity laser = new LaserProjectileEntity(pLevel,(LivingEntity) pEntity);
        laser.shootFromRotation(pEntity, pEntity.getXRot(), pEntity.getYRot(), 0.0F, 3.0F, 1.0F);
        pLevel.addFreshEntity(laser);
    }
}
