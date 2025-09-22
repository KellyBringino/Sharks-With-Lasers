package com.dontouchat.sharkswithlasers.client.render;


import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;

/*
 * Class for lines. These are stored in RenderUtils.lineToRenderList. They are then drawn each frame.
 * Any part of the program who wants to draw a line needs to append it to RenderUtils.lineToRenderList.
 *
 * Note that the positions you provide must be absolute (world coordinates).
 */
public class Line {
    public Vec3 startPos;
    public Vec3 endPos;
    public float colorR;
    public float colorG;
    public float colorB;
    public float colorA;

    public Line(Vec3 posA, Vec3 posB, float R, float G, float B, float A) {
        startPos = posA;
        endPos = posB;
        // colors
        this.colorR = R;
        this.colorG = G;
        this.colorB = B;
        this.colorA = A;
    }

    public void Draw() {
        PoseStack poseStack = new PoseStack();
        Vec3 playerPos = Minecraft.getInstance().getCameraEntity().position();
                //Utils.MC.player.getCameraPosVec(0);
        // calculate relative position using given position (absolute) - player position (absolute)
        Vec3 start = new Vec3(this.startPos.x - playerPos.x, this.startPos.y - playerPos.y, this.startPos.z - playerPos.z);
        Vec3 end = new Vec3(this.endPos.x - playerPos.x, this.endPos.y - playerPos.y, this.endPos.z - playerPos.z);
        Renderer.drawLine3D(
                poseStack.last().pose(),
                start,
                end,
                colorR, colorG, colorB, colorA
        );
    }
}

//        WorldRenderEvents.LAST.register(context -> {
//            pissFuck++;
//            Vec3d start = new Vec3d(pissFuck, 70, 0);
//            Vec3d end = new Vec3d(100, 70, 100);
//            LineRenderer.drawLine3D(context.matrixStack().peek().getPositionMatrix(), start, end, 1.0f, 0.0f, 0.0f, 1.0f);
//        });