package com.dontouchat.sharkswithlasers.entity.client;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SharkModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart shark;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart torso;

	public SharkModel(ModelPart root) {
		this.shark = root.getChild("shark");
		this.body = this.shark.getChild("body");
		this.head = this.body.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.tail = this.body.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.torso = this.body.getChild("torso");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition shark = partdefinition.addOrReplaceChild("shark", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = shark.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(50, 35).addBox(-5.5F, -6.0F, -11.0F, 11.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(77, 0).addBox(-3.0F, 0.0F, -6.5F, 6.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -10.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Mouth_r1 = jaw.addOrReplaceChild("Mouth_r1", CubeListBuilder.create().texOffs(0, 68).addBox(-3.5F, 0.0F, -7.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(76, 0).addBox(-3.0F, -1.0F, -6.5F, 6.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 31).addBox(-5.0F, -4.0F, -1.0F, 10.0F, 8.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 10.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(31, 88).addBox(-3.0F, -4.0F, -1.0F, 6.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

		PartDefinition TailTopFin_r1 = tail2.addOrReplaceChild("TailTopFin_r1", CubeListBuilder.create().texOffs(53, 57).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition TailBottomFin_r1 = tail2.addOrReplaceChild("TailBottomFin_r1", CubeListBuilder.create().texOffs(64, 0).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -10.0F, -10.0F, 12.0F, 10.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition DorsalFin_r1 = torso.addOrReplaceChild("DorsalFin_r1", CubeListBuilder.create().texOffs(64, 12).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, -1.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition FlipperRight_r1 = torso.addOrReplaceChild("FlipperRight_r1", CubeListBuilder.create().texOffs(0, 60).addBox(-12.0F, 0.0F, 0.0F, 12.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -2.0F, -1.0F, 0.0F, 0.4363F, -0.5236F));

		PartDefinition FlipperLeft_r1 = torso.addOrReplaceChild("FlipperLeft_r1", CubeListBuilder.create().texOffs(0, 53).addBox(0.0F, 0.0F, 0.0F, 12.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -2.0F, -1.0F, 0.0F, -0.4363F, 0.5236F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		shark.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

    @Override
    public ModelPart root() {
        return shark;
    }
}