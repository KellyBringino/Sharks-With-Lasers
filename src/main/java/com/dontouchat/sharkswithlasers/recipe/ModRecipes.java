package com.dontouchat.sharkswithlasers.recipe;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SharksWithLasers.MODID);

    public static final RegistryObject<RecipeSerializer<SifterRecipe>> SIFTING_SERIALIZER =
            SERIALIZERS.register("sifting", () -> SifterRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}