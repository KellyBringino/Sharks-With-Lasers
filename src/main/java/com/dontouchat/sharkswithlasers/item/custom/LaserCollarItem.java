package com.dontouchat.sharkswithlasers.item.custom;

import com.dontouchat.sharkswithlasers.entity.custom.SharkEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LaserCollarItem extends Item {
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
}
