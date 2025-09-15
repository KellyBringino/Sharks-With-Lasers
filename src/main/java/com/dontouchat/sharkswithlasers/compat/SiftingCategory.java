package com.dontouchat.sharkswithlasers.compat;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.dontouchat.sharkswithlasers.block.ModBlocks;
import com.dontouchat.sharkswithlasers.recipe.SifterRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("removal")
public class SiftingCategory implements IRecipeCategory<SifterRecipe> {
    public static final ResourceLocation UID =
            new ResourceLocation(SharksWithLasers.MODID,"sifting");
    public static final ResourceLocation TEXTURE =
            new ResourceLocation(SharksWithLasers.MODID, "textures/gui/sifter.png");

    public static final RecipeType<SifterRecipe> SIFTING_TYPE =
            new RecipeType<>(UID, SifterRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public SiftingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,4,4,169,79);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ModBlocks.SIFTER_BLOCK.get()));
    }


    @Override
    public RecipeType<SifterRecipe> getRecipeType() {
        return SIFTING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.sharks_with_lasers.sifter_block");
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder,
                          SifterRecipe sifterRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 52, 31)
                .addIngredients(sifterRecipe.getIngredients().get(0));

        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 112, 13)
                .addItemStack(sifterRecipe.getResultItem(null));
    }
}
