package com.cultivation_mod.recipes;

import net.fabricmc.fabric.impl.recipe.ingredient.ShapelessMatch;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AlchemyRecipe implements Recipe<RecipeInput> {

    private final List<Ingredient> inputs;
    private final ItemStack output;
    public AlchemyRecipe(ItemStack output, List<Ingredient> inputs){
        this.inputs = inputs;
        this.output = output;
    }

    public ItemStack getOutput() {
        return output;
    }

    public List<Ingredient> getInputs() {
        return inputs;
    }

    @Override
    public boolean matches(RecipeInput input, World world) {
        List<ItemStack> inputItems = new ArrayList<>();
        for (int i = 0; i <input.size();i++)
            inputItems.add(input.getStackInSlot(i));
        return ShapelessMatch.isMatch(inputItems,this.inputs);

    }

    @Override
    public ItemStack craft(RecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return this.getOutput().copy();
    }

    @Override
    public AlchemyRecipeSerializer getSerializer() {
        return new AlchemyRecipeSerializer();
    }

    @Override
    public RecipeType<AlchemyRecipe> getType() {
        return Type.INSTANCE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        List<Ingredient> ingredients = new ArrayList<>();
        for (Ingredient input : inputs) {
            if (!input.isEmpty()) {
                ingredients.add(input);
            }
        }
        return IngredientPlacement.forShapeless(ingredients);
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }

    public static class Type implements RecipeType<AlchemyRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "alchemy_recipe";
    }

}
