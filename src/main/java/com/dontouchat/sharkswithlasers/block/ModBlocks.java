package com.dontouchat.sharkswithlasers.block;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.block.custom.RobotSharkChassisBlock;
import com.dontouchat.sharkswithlasers.block.custom.RobotSharkHeadBlock;
import com.dontouchat.sharkswithlasers.block.custom.RobotSharkTailBlock;
import com.dontouchat.sharkswithlasers.block.custom.SifterBlock;
import com.dontouchat.sharkswithlasers.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SharksWithLasers.MODID);

    public static final RegistryObject<Block> FORGED_GLASS = registerBlock("forged_glass",
            () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)
                    .noOcclusion()
                    .lightLevel((p_50874_) -> {return 2;})));
    public static final RegistryObject<Block> RESTONE_CORE = registerBlock("redstone_core",
            () -> new PoweredBlock(BlockBehaviour.Properties.copy(Blocks.REDSTONE_BLOCK)
                .lightLevel((p_50874_) -> {return 5;})));
    public static final RegistryObject<Block> SIFTER_BLOCK = registerBlock("sifter_block",
            () -> new SifterBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).noOcclusion()));
    public static final RegistryObject<Block> ROBOT_SHARK_TAIL_BLOCK = registerBlock("robot_shark_tail_block",
            () -> new RobotSharkTailBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> ROBOT_SHARK_CHASSIS_BLOCK = registerBlock("robot_shark_chassis_block",
            () -> new RobotSharkChassisBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> ROBOT_SHARK_HEAD_BLOCK = registerBlock("robot_shark_head_block",
            () -> new RobotSharkHeadBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
