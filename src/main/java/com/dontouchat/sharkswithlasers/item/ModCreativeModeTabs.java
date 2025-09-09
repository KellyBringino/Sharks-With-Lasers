package com.dontouchat.sharkswithlasers.item;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SharksWithLasers.MODID);

    public static final RegistryObject<CreativeModeTab> SHARKS_WITH_LASERS_TAB = CREATIVE_MODE_TABS.register(
            "sharks_with_lasers_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FOCUSLENS.get()))
                    .title(Component.translatable("creativetab.sharks_with_lasers_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.SHARK_TOOTH.get());
                        pOutput.accept(ModItems.SHARK_FIN.get());
                        pOutput.accept(ModBlocks.FORGED_GLASS.get());
                        pOutput.accept(ModItems.FOCUSLENS.get());
                        pOutput.accept(ModItems.IRON_WIRE.get());
                        pOutput.accept(ModItems.IRON_MESH.get());
                        pOutput.accept(ModBlocks.SIFTER_BLOCK.get());
                        pOutput.accept(ModItems.SILICATE_DUST.get());
                        pOutput.accept(ModItems.SILICON.get());
                        pOutput.accept(ModItems.MICROCHIP.get());
                        pOutput.accept(ModItems.LASER_MODULE.get());
                        pOutput.accept(ModItems.LASER_COLLAR.get());
                        pOutput.accept(ModBlocks.RESTONE_CORE.get());
                        pOutput.accept(ModItems.SHARK_SPAWN_EGG.get());
                    })
                    .build());

    public static  void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
