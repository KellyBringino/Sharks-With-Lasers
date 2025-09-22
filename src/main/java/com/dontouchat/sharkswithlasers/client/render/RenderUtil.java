package com.dontouchat.sharkswithlasers.client.render;


import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

/*
 * Utility function for rendering. Most importantly contains lineToRenderList.
 */
public class RenderUtil {
    public static ArrayList<Line> lineToRenderList = new ArrayList<>();

    public static void drawLine(Vec3 posA, Vec3 posB,  float R, float G, float B, float A) {
        lineToRenderList.add(new Line(posA, posB, R,B,G,A));
    }

    public static void clear() {
        lineToRenderList.clear();
    }
}