package com.dontouchat.sharkswithlasers;

import com.dontouchat.sharkswithlasers.block.ModBlocks;
import com.dontouchat.sharkswithlasers.block.entity.ModBlockEntities;
import com.dontouchat.sharkswithlasers.entity.ModEntities;
import com.dontouchat.sharkswithlasers.entity.client.LaserRenderer;
import com.dontouchat.sharkswithlasers.entity.client.SharkRenderer;
import com.dontouchat.sharkswithlasers.item.ModCreativeModeTabs;
import com.dontouchat.sharkswithlasers.item.ModItems;
import com.dontouchat.sharkswithlasers.screen.ModMenuTypes;
import com.dontouchat.sharkswithlasers.screen.SifterScreen;
import com.dontouchat.sharkswithlasers.sound.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SharksWithLasers.MODID)
public class SharksWithLasers
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "sharks_with_lasers";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public SharksWithLasers(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModSounds.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.SHARK.get(), SharkRenderer::new);

            EntityRenderers.register(ModEntities.LASER.get(), LaserRenderer::new);
            MenuScreens.register(ModMenuTypes.SIFTER_MENU.get(), SifterScreen::new);
        }
    }
}
