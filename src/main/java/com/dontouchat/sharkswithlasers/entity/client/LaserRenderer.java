package com.dontouchat.sharkswithlasers.entity.client;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.entity.custom.LaserProjectileEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LaserRenderer extends ArrowRenderer<LaserProjectileEntity> {
    public LaserRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    @SuppressWarnings("removal")
    public ResourceLocation getTextureLocation(LaserProjectileEntity pEntity) {
        return new ResourceLocation(SharksWithLasers.MODID, "textures/entity/projectile/laser.png");
    }
}
