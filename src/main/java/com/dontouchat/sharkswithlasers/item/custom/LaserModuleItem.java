package com.dontouchat.sharkswithlasers.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LaserModuleItem extends Item {

    public LaserModuleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        Vec3 playerEyeLoc = new Vec3(pPlayer.getX(), pPlayer.getEyeY(), pPlayer.getZ());
        Vec3 playerLookDir = pPlayer.getLookAngle();
        HitResult hit =  pLevel.clip(new ClipContext(playerEyeLoc,playerEyeLoc.add(playerLookDir),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null));

        Vec3 laserSpot = playerEyeLoc.add(playerLookDir);
        if (hit.getType() == HitResult.Type.BLOCK || hit.getType() == HitResult.Type.ENTITY)
        {
            laserSpot = hit.getLocation();
        }


        pPlayer.startUsingItem(pUsedHand);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.SPEAR;
    }
}
