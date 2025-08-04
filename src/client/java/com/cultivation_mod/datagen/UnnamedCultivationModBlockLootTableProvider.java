package com.cultivation_mod.datagen;

import com.cultivation_mod.CultivationModBlocks;
import com.cultivation_mod.CultivationModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class UnnamedCultivationModBlockLootTableProvider extends FabricBlockLootTableProvider {
    protected UnnamedCultivationModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(CultivationModBlocks.JADE_BLOCK);
        addDrop(CultivationModBlocks.JADE_BLOCK_WALL);
        addDrop(CultivationModBlocks.JADE_BLOCK_SLAB);
        addDrop(CultivationModBlocks.JADE_BLOCK_STAIRS);
        addDrop(CultivationModBlocks.POLISHED_JADE_BLOCK);
        addDrop(CultivationModBlocks.POLISHED_JADE_BLOCK_WALL);
        addDrop(CultivationModBlocks.POLISHED_JADE_BLOCK_SLAB);
        addDrop(CultivationModBlocks.POLISHED_JADE_BLOCK_STAIRS);
        addDrop(CultivationModBlocks.JADE_BRICKS);
        addDrop(CultivationModBlocks.JADE_BRICK_WALL);
        addDrop(CultivationModBlocks.JADE_BRICK_SLAB);
        addDrop(CultivationModBlocks.JADE_BRICK_STAIRS);
        addDrop(CultivationModBlocks.MOSSY_JADE_BRICKS);
        addDrop(CultivationModBlocks.MOSSY_JADE_BRICK_WALL);
        addDrop(CultivationModBlocks.MOSSY_JADE_BRICK_SLAB);
        addDrop(CultivationModBlocks.MOSSY_JADE_BRICK_STAIRS);
        addDrop(CultivationModBlocks.CRACKED_JADE_BRICKS);
        addDrop(CultivationModBlocks.CRACKED_JADE_BRICK_WALL);
        addDrop(CultivationModBlocks.CRACKED_JADE_BRICK_SLAB);
        addDrop(CultivationModBlocks.CRACKED_JADE_BRICK_STAIRS);

        addDrop(CultivationModBlocks.JADE_CAULDRON);
    }
}
