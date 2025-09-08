package com.dontouchat.sharkswithlasers.entity;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.entity.custom.LaserProjectileEntity;
import com.dontouchat.sharkswithlasers.entity.custom.RobotSharkEntity;
import com.dontouchat.sharkswithlasers.entity.custom.SharkEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SharksWithLasers.MODID);

    public static final RegistryObject<EntityType<SharkEntity>> SHARK =
            ENTITY_TYPES.register("shark", () -> EntityType.Builder.of(SharkEntity::new, MobCategory.MONSTER)
                    .sized(1.7f,0.7f).build("shark"));
    public static  final RegistryObject<EntityType<RobotSharkEntity>> ROBOT_SHARK =
            ENTITY_TYPES.register("robot_shark", () -> EntityType.Builder.of(RobotSharkEntity::new, MobCategory.MONSTER)
                    .sized(1.7f,0.7f).build("robot_shark"));
    public static final RegistryObject<EntityType<LaserProjectileEntity>> LASER =
            ENTITY_TYPES.register("laser_projectile", () -> EntityType.Builder
                    .<LaserProjectileEntity>of(LaserProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f,0.5f).build("laser_projectile"));

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}
