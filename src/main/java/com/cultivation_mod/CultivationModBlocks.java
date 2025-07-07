package com.cultivation_mod;

import com.cultivation_mod.blocks.JadeCauldron;
import com.cultivation_mod.blocks.SpiritHerb;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class CultivationModBlocks {
    public static final IntProperty LOCAL_ELEMENT = IntProperty.of("local_element",0,4) ;
    public static final IntProperty LOCAL_QI = IntProperty.of("local_qi",0,500) ;
    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(CultivationModItems.CULTIVATION_MOD_GROUP_KEY).register((itemGroup) ->
        {
            itemGroup.add(CultivationModBlocks.JADE_BLOCK.asItem());
            itemGroup.add(CultivationModBlocks.JADE_BLOCK_SLAB.asItem());
            itemGroup.add(CultivationModBlocks.JADE_BLOCK_STAIRS.asItem());
            itemGroup.add(CultivationModBlocks.JADE_BLOCK_WALL.asItem());
            itemGroup.add(CultivationModBlocks.POLISHED_JADE_BLOCK.asItem());
            itemGroup.add(CultivationModBlocks.POLISHED_JADE_BLOCK_SLAB.asItem());
            itemGroup.add(CultivationModBlocks.POLISHED_JADE_BLOCK_STAIRS.asItem());
            itemGroup.add(CultivationModBlocks.POLISHED_JADE_BLOCK_WALL.asItem());
            itemGroup.add(CultivationModBlocks.JADE_BRICKS.asItem());
            itemGroup.add(CultivationModBlocks.JADE_BRICK_SLAB.asItem());
            itemGroup.add(CultivationModBlocks.JADE_BRICK_STAIRS.asItem());
            itemGroup.add(CultivationModBlocks.JADE_BRICK_WALL.asItem());
            itemGroup.add(CultivationModBlocks.CRACKED_JADE_BRICKS.asItem());
            itemGroup.add(CultivationModBlocks.MOSSY_JADE_BRICKS.asItem());
            itemGroup.add(CultivationModBlocks.JADE_CAULDRON.asItem());
        });

    }

    public static <T extends Block> T register(Function<AbstractBlock.Settings, T> constructor, AbstractBlock.Settings blockSettings, String name) {
        Identifier id = Identifier.of(CultivationMod.MOD_ID, name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        Block.Settings settings = blockSettings.registryKey(key);
        T block = constructor.apply(settings);

        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);
        Item.Settings itemSettings = new Item.Settings()
                .useBlockPrefixedTranslationKey()
                .registryKey(itemKey);
        Registry.register(Registries.ITEM, itemKey, new BlockItem(block, itemSettings));

        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block register(Block.Settings blockSettings, String name) {
        return register(Block::new,blockSettings,name);
    }

    public static final Block JADE_BLOCK = register(
            Block.Settings.create().sounds(BlockSoundGroup.STONE),
            "jade_block"
    );

    public static final Block JADE_BLOCK_SLAB = register(SlabBlock::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "jade_block_slab"
    );

    public static final Block JADE_BLOCK_STAIRS = register((Block.Settings settings)-> new StairsBlock(JADE_BLOCK.getDefaultState(), settings),
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "jade_block_stairs"
    );

    public static final Block JADE_BLOCK_WALL = register(WallBlock::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "jade_block_wall"
    );

    public static final Block POLISHED_JADE_BLOCK = register(
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "polished_jade_block"
    );

    public static final Block POLISHED_JADE_BLOCK_SLAB = register(SlabBlock::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "polished_jade_block_slab"
    );

    public static final Block POLISHED_JADE_BLOCK_STAIRS = register((Block.Settings settings)-> new StairsBlock(POLISHED_JADE_BLOCK.getDefaultState(), settings),
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "polished_jade_block_stairs"
    );

    public static final Block POLISHED_JADE_BLOCK_WALL = register(WallBlock::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "polished_jade_block_wall"
    );

    public static final Block JADE_BRICKS = register(
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "jade_bricks"
    );

    public static final Block JADE_BRICK_SLAB = register(SlabBlock::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "jade_brick_slab"
    );

    public static final Block JADE_BRICK_STAIRS = register((Block.Settings settings)-> new StairsBlock(JADE_BRICKS.getDefaultState(), settings),
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "jade_brick_stairs"
    );

    public static final Block JADE_BRICK_WALL = register(WallBlock::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "jade_brick_wall"
    );

    public static final Block CRACKED_JADE_BRICKS = register(
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "cracked_jade_bricks"
    );

    public static final Block MOSSY_JADE_BRICKS = register(
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "mossy_jade_bricks"
    );

    public static final JadeCauldron JADE_CAULDRON = register(JadeCauldron::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "jade_cauldron"
    );

    public static final SpiritHerb SPIRIT_HERB = register(SpiritHerb::new,
            Block.Settings.create().nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP),
            "spirit_herb"
    );

}
