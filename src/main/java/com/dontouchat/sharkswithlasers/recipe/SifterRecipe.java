package com.dontouchat.sharkswithlasers.recipe;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SifterRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final NonNullList<ItemStack> spareItems;
    private final ResourceLocation id;

    private final float SPARE_CHANCE = 0.15f;

    public SifterRecipe(NonNullList<Ingredient> inputItems, ItemStack output,NonNullList<ItemStack> spareItems, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.spareItems = spareItems;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        return inputItems.get(0).test(pContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }
    public NonNullList<ItemStack> getSpares() {
        return spareItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }
    public ItemStack getSpareItem(RegistryAccess pRegistryAccess, Level plevel) {
        int which = plevel.getRandom().nextInt(spareItems.size());
        return  plevel.getRandom().nextFloat() <= SPARE_CHANCE ?
                spareItems.get(which).copy() :
                ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SifterRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "sifting";
    }

    public static class Serializer implements RecipeSerializer<SifterRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        @SuppressWarnings("removal")
        public static final ResourceLocation ID = new ResourceLocation(SharksWithLasers.MODID, "sifting");

        @Override
        public SifterRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            JsonArray ingredientsSpare = GsonHelper.getAsJsonArray(pSerializedRecipe, "spares");
            NonNullList<ItemStack> spares = NonNullList.withSize(ingredientsSpare.size(), ItemStack.EMPTY);
            for(int i = 0; i < ingredientsSpare.size(); i++) {
                spares.set(i, ShapedRecipe.itemStackFromJson((JsonObject) ingredientsSpare.get(i)));
            }

            return new SifterRecipe(inputs, output, spares, pRecipeId);
        }

        @Override
        public @Nullable SifterRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();

            NonNullList<ItemStack> spares = NonNullList.withSize(pBuffer.readInt(), ItemStack.EMPTY);
            for(int i = 0; i < spares.size(); i++) {
                spares.set(i, pBuffer.readItem());
            }

            return new SifterRecipe(inputs, output, spares, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SifterRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());
            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);

            pBuffer.writeInt(pRecipe.spareItems.size());
            for (ItemStack itemStack : pRecipe.getSpares()) {
                pBuffer.writeItemStack(itemStack,false);
            }
        }
    }
}
