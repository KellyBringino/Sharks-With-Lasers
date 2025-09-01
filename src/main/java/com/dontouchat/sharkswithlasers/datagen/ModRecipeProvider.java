package com.dontouchat.sharkswithlasers.datagen;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.block.ModBlocks;
import com.dontouchat.sharkswithlasers.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LASER_MODULE.get())
                .pattern("LG")
                .pattern("IM")
                .define('L', ModItems.FOCUSLENS.get())
                .define('G', Items.GLOWSTONE_DUST)
                .define('M', ModItems.MICROCHIP.get())
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.MICROCHIP.get()), has(ModItems.MICROCHIP.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RESTONE_CORE.get())
                .pattern("RRR")
                .pattern("RRR")
                .pattern("RRR")
                .define('R', Items.REDSTONE_BLOCK)
                .unlockedBy(getHasName(Items.REDSTONE_BLOCK), has(Items.REDSTONE_BLOCK))
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
