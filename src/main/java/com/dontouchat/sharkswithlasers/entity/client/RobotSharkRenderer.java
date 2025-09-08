package com.dontouchat.sharkswithlasers.entity.client;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.entity.custom.RobotSharkEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RobotSharkRenderer extends MobRenderer<RobotSharkEntity, RobotSharkModel<RobotSharkEntity>> {
    public RobotSharkRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RobotSharkModel<>(pContext.bakeLayer(ModModelLayers.ROBOT_SHARK_LAYER)), 1f);
        this.addLayer(new RobotSharkCollarLayer(this,pContext.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(RobotSharkEntity pEntity) {
        return new ResourceLocation(SharksWithLasers.MODID, "textures/entity/robot_shark.png");
    }
}
