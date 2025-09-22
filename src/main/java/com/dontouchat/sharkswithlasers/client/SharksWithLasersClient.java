package com.dontouchat.sharkswithlasers.client;


import com.dontouchat.sharkswithlasers.client.render.Line;
import com.dontouchat.sharkswithlasers.client.render.RenderUtil;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SharksWithLasersClient {
    @SubscribeEvent
    public static void onWorldRenderEnd(RenderLevelStageEvent event){
        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER){
            for (Line line : RenderUtil.lineToRenderList) {
                line.Draw();
            }
            RenderUtil.clear();
        }
    }
}