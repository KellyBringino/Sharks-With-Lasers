package com.dontouchat.sharkswithlasers.item;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.item.custom.LaserModuleItem;
import net.minecraft.world.item.Item;
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

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
