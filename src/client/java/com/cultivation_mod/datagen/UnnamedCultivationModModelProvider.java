package com.cultivation_mod.datagen;

import com.cultivation_mod.CultivationModBlocks;
import com.cultivation_mod.CultivationModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class UnnamedCultivationModModelProvider extends FabricModelProvider {
    public UnnamedCultivationModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // jade block stairs
        // jade block slab
        // jade block wall
        blockStateModelGenerator.registerSimpleCubeAll(CultivationModBlocks.JADE_BRICKS);
        // jade brick stairs
        // jade brick slab
        // jade brick wall
        blockStateModelGenerator.registerSimpleCubeAll(CultivationModBlocks.CRACKED_JADE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(CultivationModBlocks.MOSSY_JADE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(CultivationModBlocks.POLISHED_JADE_BLOCK);
        // polished jade block stairs
        // polished jade block slab
        // polished jade block wall
    }


    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(CultivationModItems.SPIRIT_HERB_FIRE, Models.GENERATED);
        itemModelGenerator.register(CultivationModItems.SPIRIT_HERB_WATER, Models.GENERATED);
        itemModelGenerator.register(CultivationModItems.SPIRIT_HERB_AIR, Models.GENERATED);
        itemModelGenerator.register(CultivationModItems.SPIRIT_HERB_EARTH, Models.GENERATED);
        itemModelGenerator.register(CultivationModItems.SPIRIT_HERB_LIGHTNING, Models.GENERATED);
        itemModelGenerator.register(CultivationModItems.SPIRIT_HERB_SEEDS, Models.GENERATED);

        itemModelGenerator.register(CultivationModItems.SPIRIT_STONE_FIRE, Models.GENERATED);
        itemModelGenerator.register(CultivationModItems.SPIRIT_STONE_WATER, Models.GENERATED);
        itemModelGenerator.register(CultivationModItems.SPIRIT_STONE_AIR, Models.GENERATED);
        itemModelGenerator.register(CultivationModItems.SPIRIT_STONE_EARTH, Models.GENERATED);
        itemModelGenerator.register(CultivationModItems.SPIRIT_STONE_LIGHTNING, Models.GENERATED);

        itemModelGenerator.register(CultivationModItems.QI_EFFICIENCY_PILL, Models.GENERATED);
        itemModelGenerator.register(CultivationModItems.ROOT_REGROWTH_ELIXIR, Models.GENERATED);
        itemModelGenerator.register(CultivationModItems.SPIRIT_HERB_MASH, Models.GENERATED);
    }

    @Override
    public String getName() {
        return "Unnamed Cultivation Mod Model Provider";
    }
}
