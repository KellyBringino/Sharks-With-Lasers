package com.dontouchat.sharkswithlasers.event;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.entity.ModEntities;
import com.dontouchat.sharkswithlasers.entity.custom.RobotSharkEntity;
import com.dontouchat.sharkswithlasers.entity.custom.SharkEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SharksWithLasers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.SHARK.get(), SharkEntity.createAttributes().build());
        event.put(ModEntities.ROBOT_SHARK.get(), RobotSharkEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event){
        event.register(
                ModEntities.SHARK.get(),
                SpawnPlacements.getPlacementType(ModEntities.SHARK.get()),
                Heightmap.Types.OCEAN_FLOOR,
                AbstractFish::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
