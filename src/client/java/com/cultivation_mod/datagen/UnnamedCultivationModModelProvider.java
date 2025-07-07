package com.cultivation_mod.datagen;

import com.cultivation_mod.CultivationModBlocks;
import com.cultivation_mod.CultivationModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.util.Identifier;

public class UnnamedCultivationModModelProvider extends FabricModelProvider {
    public UnnamedCultivationModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        registerStairs(blockStateModelGenerator,CultivationModBlocks.JADE_BLOCK_STAIRS,CultivationModBlocks.JADE_BLOCK);
        registerSlab(blockStateModelGenerator,CultivationModBlocks.JADE_BLOCK_SLAB,CultivationModBlocks.JADE_BLOCK);
        registerWall(blockStateModelGenerator,CultivationModBlocks.JADE_BLOCK_WALL,CultivationModBlocks.JADE_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(CultivationModBlocks.JADE_BRICKS);
        registerStairs(blockStateModelGenerator,CultivationModBlocks.JADE_BRICK_STAIRS,CultivationModBlocks.JADE_BRICKS);
        registerSlab(blockStateModelGenerator,CultivationModBlocks.JADE_BRICK_SLAB,CultivationModBlocks.JADE_BRICKS);
        registerWall(blockStateModelGenerator,CultivationModBlocks.JADE_BRICK_WALL,CultivationModBlocks.JADE_BRICKS);

        blockStateModelGenerator.registerSimpleCubeAll(CultivationModBlocks.CRACKED_JADE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(CultivationModBlocks.MOSSY_JADE_BRICKS);

        blockStateModelGenerator.registerSimpleCubeAll(CultivationModBlocks.POLISHED_JADE_BLOCK);
        registerStairs(blockStateModelGenerator,CultivationModBlocks.POLISHED_JADE_BLOCK_STAIRS,CultivationModBlocks.POLISHED_JADE_BLOCK);
        registerSlab(blockStateModelGenerator,CultivationModBlocks.POLISHED_JADE_BLOCK_SLAB,CultivationModBlocks.POLISHED_JADE_BLOCK);
        registerWall(blockStateModelGenerator,CultivationModBlocks.POLISHED_JADE_BLOCK_WALL,CultivationModBlocks.POLISHED_JADE_BLOCK);
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

    public void registerStairs(BlockStateModelGenerator blockStateModelGenerator, Block block, Block baseBlock){
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(block,
                Models.INNER_STAIRS.upload(block, TextureMap.all(baseBlock),blockStateModelGenerator.modelCollector),
                Models.STAIRS.upload(block,TextureMap.all(baseBlock),blockStateModelGenerator.modelCollector),
                Models.OUTER_STAIRS.upload(block, TextureMap.all(baseBlock),blockStateModelGenerator.modelCollector)));
    }
    public void registerWall(BlockStateModelGenerator blockStateModelGenerator, Block block, Block baseBlock){
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(block,
                Models.TEMPLATE_WALL_POST.upload(block, TextureMap.all(baseBlock),blockStateModelGenerator.modelCollector),
                Models.TEMPLATE_WALL_SIDE.upload(block,TextureMap.all(baseBlock),blockStateModelGenerator.modelCollector),
                Models.TEMPLATE_WALL_SIDE_TALL.upload(block, TextureMap.all(baseBlock),blockStateModelGenerator.modelCollector)));
    }
    public void registerSlab(BlockStateModelGenerator blockStateModelGenerator, Block block, Block baseBlock){
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(block,
                Models.SLAB.upload(block,TextureMap.all(baseBlock),blockStateModelGenerator.modelCollector),
                Models.SLAB_TOP.upload(block,TextureMap.all(baseBlock),blockStateModelGenerator.modelCollector),
                Models.CUBE_ALL.upload(block,"_full",TextureMap.all(baseBlock),blockStateModelGenerator.modelCollector)));
    }
}
