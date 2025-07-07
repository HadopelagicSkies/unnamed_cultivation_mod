package com.cultivation_mod.datagen;

import com.cultivation_mod.CultivationModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class UnnamedCultivationModBlockTagProvider extends FabricTagProvider<Block>{


    public UnnamedCultivationModBlockTagProvider(FabricDataOutput output,CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BLOCK,registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(CultivationModBlocks.JADE_BLOCK_WALL)
                .add(CultivationModBlocks.POLISHED_JADE_BLOCK_WALL)
                .add(CultivationModBlocks.JADE_BRICK_WALL)
                .add(CultivationModBlocks.MOSSY_JADE_BRICK_WALL)
                .add(CultivationModBlocks.CRACKED_JADE_BRICK_WALL);
    }
}
