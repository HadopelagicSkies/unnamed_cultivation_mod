package com.cultivation_mod.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public class AlchemyRecipeInput implements RecipeInput {

    private final List<ItemStack> stacks;
    private final RecipeFinder matcher = new RecipeFinder();
    private final int stackCount;

    private AlchemyRecipeInput(List<ItemStack> stacks){
        this.stacks = stacks;
        this.stackCount = stacks.size();

        for(ItemStack itemStack : stacks) {
            if (!itemStack.isEmpty()) {
                this.matcher.addInput(itemStack, 1);
            }
        }
    }

    public static AlchemyRecipeInput create(List<ItemStack> stacks){
        return new AlchemyRecipeInput(stacks);
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.stacks.get(slot);
    }

    @Override
    public int size() {
        return stackCount;
    }

    public RecipeFinder getRecipeMatcher() {
        return this.matcher;
    }

    public List<ItemStack> getStacks() {
        return this.stacks;
    }


}
