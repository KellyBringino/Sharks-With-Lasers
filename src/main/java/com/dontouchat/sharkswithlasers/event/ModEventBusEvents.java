package com.dontouchat.sharkswithlasers.event;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.entity.ModEntities;
import com.dontouchat.sharkswithlasers.entity.custom.RobotSharkEntity;
import com.dontouchat.sharkswithlasers.entity.custom.SharkEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SharksWithLasers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.SHARK.get(), SharkEntity.createAttributes().build());
        event.put(ModEntities.ROBOT_SHARK.get(), RobotSharkEntity.createAttributes().build());
    }
}
