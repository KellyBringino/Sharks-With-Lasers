package com.dontouchat.sharkswithlasers.item;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.entity.ModEntities;
import com.dontouchat.sharkswithlasers.item.custom.LaserCollarItem;
import com.dontouchat.sharkswithlasers.item.custom.LaserModuleItem;
import com.dontouchat.sharkswithlasers.item.custom.RobotCoreItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SharksWithLasers.MODID);

    public static final RegistryObject<Item> FOCUSLENS = ITEMS.register("focuslens",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILICATE_DUST = ITEMS.register("silicate_dust",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> IRON_WIRE = ITEMS.register("iron_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_MESH = ITEMS.register("iron_mesh",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SILICON = ITEMS.register("silicon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MICROCHIP = ITEMS.register("microchip",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LASER_MODULE = ITEMS.register("laser_module",
            () -> new LaserModuleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LASER_COLLAR = ITEMS.register("laser_collar",
            () -> new LaserCollarItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SHARK_TOOTH = ITEMS.register("shark_tooth",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHARK_FIN = ITEMS.register("shark_fin",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> IRON_SHARK_FIN = ITEMS.register("iron_shark_fin",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROBOT_CORE = ITEMS.register("robot_core",
            () -> new RobotCoreItem(new Item.Properties()));

    public static final RegistryObject<Item> SHARK_SPAWN_EGG = ITEMS.register("shark_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SHARK,0x7e9680,0xc5d1c5,
                    new Item.Properties()));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
