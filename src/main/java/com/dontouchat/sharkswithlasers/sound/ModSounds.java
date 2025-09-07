package com.dontouchat.sharkswithlasers.sound;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SharksWithLasers.MODID);

    public static final RegistryObject<SoundEvent> LASER_SHOOT =
            registerSoundEvents("laser_shoot");
    public static final RegistryObject<SoundEvent> LASER_HIT =
            registerSoundEvents("laser_hit");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name){
        return SOUND_EVENTS.register(name, () -> SoundEvent
                .createVariableRangeEvent(new ResourceLocation(SharksWithLasers.MODID, name)));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
