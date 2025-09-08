package com.dontouchat.sharkswithlasers.entity.client;

import com.dontouchat.sharkswithlasers.entity.custom.RobotSharkEntity;
import com.dontouchat.sharkswithlasers.entity.custom.SharkEntity;
import com.dontouchat.sharkswithlasers.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RobotSharkCollarLayer extends RenderLayer<RobotSharkEntity, RobotSharkModel<RobotSharkEntity>> {
    private final ItemInHandRenderer itemInHandRenderer;

    public RobotSharkCollarLayer(RenderLayerParent<RobotSharkEntity, RobotSharkModel<RobotSharkEntity>> pRenderer, ItemInHandRenderer pBlockRenderer) {
        super(pRenderer);
        this.itemInHandRenderer = pBlockRenderer;
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, RobotSharkEntity pLivingEntity,
                       float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks,
                       float pNetHeadYaw, float pHeadPitch) {
        if (pLivingEntity.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LASER_COLLAR.get())) {
            pPoseStack.pushPose();
            ModelPart modelpart = this.getParentModel().getCollarPoint();
            modelpart.translateAndRotate(pPoseStack);
            pPoseStack.translate(0.0F,1.0F,-0.5F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
            this.itemInHandRenderer.renderItem(
                    pLivingEntity,
                    new ItemStack(ModItems.LASER_COLLAR.get()),
                    ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                    false,
                    pPoseStack,
                    pBuffer,
                    pPackedLight);
            pPoseStack.popPose();
        }
    }
}
