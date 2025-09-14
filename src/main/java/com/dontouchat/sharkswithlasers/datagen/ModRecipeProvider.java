package com.dontouchat.sharkswithlasers.datagen;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.block.ModBlocks;
import com.dontouchat.sharkswithlasers.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput)
    {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter)
    {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FOCUSLENS.get(),4)
                .requires(ModBlocks.FORGED_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.FORGED_GLASS.get()), has(ModBlocks.FORGED_GLASS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_WIRE.get())
                .pattern("II")
                .pattern("II")
                .define('I', Items.IRON_NUGGET)
                .unlockedBy(getHasName(Items.IRON_NUGGET), has(Items.IRON_NUGGET))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WIRING.get())
                .pattern("RC")
                .define('R', Items.REDSTONE)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.IRON_NUGGET), has(Items.IRON_NUGGET))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_MESH.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', ModItems.IRON_WIRE.get())
                .unlockedBy(getHasName(ModItems.IRON_WIRE.get()), has(ModItems.IRON_WIRE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SIFTER_BLOCK.get())
                .pattern("M")
                .pattern("S")
                .define('M', ModItems.IRON_MESH.get())
                .define('S', Items.SCAFFOLDING)
                .unlockedBy(getHasName(ModItems.IRON_MESH.get()), has(ModItems.IRON_MESH.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MICROCHIP.get())
                .pattern("SSS")
                .pattern("SRS")
                .pattern("SSS")
                .define('S', ModItems.SILICON.get())
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(ModItems.SILICON.get()), has(ModItems.SILICON.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MOTHERBOARD.get())
                .pattern("MMM")
                .pattern("MRM")
                .pattern("MMM")
                .define('M', ModItems.MICROCHIP.get())
                .define('R', ModBlocks.RESTONE_CORE.get())
                .unlockedBy(getHasName(ModItems.MICROCHIP.get()), has(ModItems.MICROCHIP.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LASER_MODULE.get())
                .pattern("LG")
                .pattern("IM")
                .define('L', ModItems.FOCUSLENS.get())
                .define('G', Items.GLOWSTONE_DUST)
                .define('M', ModItems.MICROCHIP.get())
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.MICROCHIP.get()), has(ModItems.MICROCHIP.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LASER_COLLAR.get())
                .pattern("ILI")
                .pattern("IWI")
                .pattern("III")
                .define('L', ModItems.LASER_MODULE.get())
                .define('I', ModItems.IRON_WIRE.get())
                .define('W', Items.BLACK_WOOL)
                .unlockedBy(getHasName(ModItems.LASER_MODULE.get()), has(ModItems.LASER_MODULE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RESTONE_CORE.get())
                .pattern("RRR")
                .pattern("R R")
                .pattern("RRR")
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REDSTONE_SINGULARITY.get())
                .pattern("RRR")
                .pattern("R R")
                .pattern("RRR")
                .define('R', ModBlocks.RESTONE_CORE.get())
                .unlockedBy(getHasName(ModBlocks.RESTONE_CORE.get()), has(ModBlocks.RESTONE_CORE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_SHARK_FIN.get())
                .pattern("I ")
                .pattern("FI")
                .define('I', Items.IRON_INGOT)
                .define('F', ModItems.SHARK_FIN.get())
                .unlockedBy(getHasName(ModItems.SHARK_FIN.get()), has(ModItems.SHARK_FIN.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_SHARK_JAW.get())
                .pattern("TTT")
                .pattern("TIT")
                .pattern("TTT")
                .define('I', Items.IRON_INGOT)
                .define('T', ModItems.SHARK_TOOTH.get())
                .unlockedBy(getHasName(ModItems.SHARK_TOOTH.get()), has(ModItems.SHARK_TOOTH.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ROBOT_SHARK_BRAIN.get())
                .pattern("IFI")
                .pattern("IMI")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('M', ModItems.MOTHERBOARD.get())
                .define('F', ModItems.SHARK_FIN.get())
                .unlockedBy(getHasName(ModItems.MOTHERBOARD.get()), has(ModItems.MOTHERBOARD.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ROBOT_CORE.get())
                .pattern("IWI")
                .pattern("WRW")
                .pattern("IWI")
                .define('R', ModItems.REDSTONE_SINGULARITY.get())
                .define('W', ModItems.WIRING.get())
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.REDSTONE_SINGULARITY.get()), has(ModItems.REDSTONE_SINGULARITY.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ROBOT_EYE.get())
                .pattern("IL")
                .pattern("W ")
                .define('L', ModItems.FOCUSLENS.get())
                .define('W', ModItems.WIRING.get())
                .define('I', Items.IRON_NUGGET)
                .unlockedBy(getHasName(ModItems.REDSTONE_SINGULARITY.get()), has(ModItems.REDSTONE_SINGULARITY.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ROBOT_SHARK_TAIL_BLOCK.get())
                .pattern("  F")
                .pattern("II ")
                .pattern("  F")
                .define('I', Blocks.IRON_BLOCK)
                .define('F', ModItems.IRON_SHARK_FIN.get())
                .unlockedBy(getHasName(ModItems.IRON_SHARK_FIN.get()), has(ModItems.IRON_SHARK_FIN.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ROBOT_SHARK_CHASSIS_BLOCK.get())
                .pattern(" I ")
                .pattern("FWF")
                .pattern(" I ")
                .define('I', Blocks.IRON_BLOCK)
                .define('F', ModItems.IRON_SHARK_FIN.get())
                .define('W', ModItems.WIRING.get())
                .unlockedBy(getHasName(ModItems.IRON_SHARK_FIN.get()), has(ModItems.IRON_SHARK_FIN.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ROBOT_SHARK_HEAD_BLOCK.get())
                .pattern("EBE")
                .pattern("IJI")
                .define('I', Blocks.IRON_BLOCK)
                .define('J', ModItems.IRON_SHARK_JAW.get())
                .define('B', ModItems.ROBOT_SHARK_BRAIN.get())
                .define('E', ModItems.ROBOT_EYE.get())
                .unlockedBy(getHasName(ModItems.ROBOT_SHARK_BRAIN.get()), has(ModItems.ROBOT_SHARK_BRAIN.get()))
                .save(pWriter);
        SimpleCookingRecipeBuilder.generic(Ingredient.of(Items.GLASS),
            RecipeCategory.MISC, ModBlocks.FORGED_GLASS.get(),
            0.1f,200, RecipeSerializer.SMELTING_RECIPE)
            .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
            .save(pWriter, SharksWithLasers.MODID +
                ":" + getItemName(ModBlocks.FORGED_GLASS.get()) + "_from_smelting_" + getItemName(Items.GLASS));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ModItems.SILICATE_DUST.get()),
            RecipeCategory.MISC, ModItems.SILICON.get(),
            0.1f,200, RecipeSerializer.SMELTING_RECIPE)
            .unlockedBy(getHasName(ModItems.SILICATE_DUST.get()), has(ModItems.SILICATE_DUST.get()))
            .save(pWriter, SharksWithLasers.MODID +
                ":" + getItemName(ModItems.SILICON.get()) + "_from_smelting_" + getItemName(ModItems.SILICATE_DUST.get()));
    }
}
