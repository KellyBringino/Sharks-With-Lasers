package com.dontouchat.sharkswithlasers.datagen;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SharksWithLasers.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.FOCUSLENS);
        simpleItem(ModItems.IRON_WIRE);
        simpleItem(ModItems.IRON_MESH);
        simpleItem(ModItems.LASER_MODULE);
        simpleItem(ModItems.MICROCHIP);
        simpleItem(ModItems.SILICATE_DUST);
        simpleItem(ModItems.SILICON);
        simpleItem(ModItems.SHARK_TOOTH);
        simpleItem(ModItems.SHARK_FIN);
        simpleItem(ModItems.IRON_SHARK_FIN);
        withExistingParent(ModItems.SHARK_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item)
    {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(SharksWithLasers.MODID,"item/" + item.getId().getPath()));
    }
}
