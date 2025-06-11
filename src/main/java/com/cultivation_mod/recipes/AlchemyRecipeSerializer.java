package com.cultivation_mod.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;

public class AlchemyRecipeSerializer implements RecipeSerializer<AlchemyRecipe> {
    private final MapCodec<AlchemyRecipe> codec;
    private final PacketCodec<RegistryByteBuf, AlchemyRecipe> packetCodec;
    
    public AlchemyRecipeSerializer(){
        this.codec = RecordCodecBuilder.mapCodec((instance) -> {
            var parameters = instance.group(
                    ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(AlchemyRecipe::getOutput),
                    Codec.list(Ingredient.CODEC).fieldOf("ingredients").forGetter(AlchemyRecipe::getInputs));
            return parameters.apply(instance, AlchemyRecipe::new);
        });

        this.packetCodec = PacketCodec.tuple(
                ItemStack.PACKET_CODEC, AlchemyRecipe::getOutput,
                Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()), AlchemyRecipe::getInputs,
                AlchemyRecipe::new);
    }
    
    @Override
    public MapCodec<AlchemyRecipe> codec() {
        return this.codec;
    }

    @Override
    public PacketCodec<RegistryByteBuf, AlchemyRecipe> packetCodec() {
        return this.packetCodec;
    }

    public static final AlchemyRecipeSerializer INSTANCE = new AlchemyRecipeSerializer();
    public static final String ID = "alchemy_recipe";

}
