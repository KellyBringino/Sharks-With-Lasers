package com.dontouchat.sharkswithlasers.entity.client;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.entity.custom.SharkEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SharkRenderer extends MobRenderer<SharkEntity, SharkModel<SharkEntity>> {
    public SharkRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SharkModel<>(pContext.bakeLayer(ModModelLayers.SHARK_LAYER)), 1f);
        this.addLayer(new SharkCollarLayer(this,pContext.getItemInHandRenderer()));
    }

    @Override
    @SuppressWarnings("removal")
    public ResourceLocation getTextureLocation(SharkEntity pEntity) {
        return new ResourceLocation(SharksWithLasers.MODID, "textures/entity/shark.png");
    }
}
