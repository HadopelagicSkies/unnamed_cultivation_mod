package com.cultivation_mod;

import com.cultivation_mod.blocks.JadeCauldron;
import com.cultivation_mod.blocks.SpiritHerb;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class CultivationModBlocks {
    public static final RegistryKey<ItemGroup> CULTIVATION_MOD_DECORATIVE_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(CultivationMod.MOD_ID, "item_group_decorative"));
    public static final ItemGroup CULTIVATION_MOD_DECORATIVE_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(CultivationModBlocks.JADE_BRICKS.asItem()))
            .displayName(Text.translatable("itemGroup.cultivation_mod_decorative"))
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, CULTIVATION_MOD_DECORATIVE_GROUP_KEY, CULTIVATION_MOD_DECORATIVE_GROUP);
        ItemGroupEvents.modifyEntriesEvent(CultivationModItems.CULTIVATION_MOD_GROUP_KEY).register((itemGroup) ->
        {
            itemGroup.add(CultivationModBlocks.JADE_CAULDRON.asItem());
        });

        ItemGroupEvents.modifyEntriesEvent(CULTIVATION_MOD_DECORATIVE_GROUP_KEY).register((itemGroup) ->
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
            itemGroup.add(CultivationModBlocks.CRACKED_JADE_BRICK_SLAB.asItem());
            itemGroup.add(CultivationModBlocks.CRACKED_JADE_BRICK_STAIRS.asItem());
            itemGroup.add(CultivationModBlocks.CRACKED_JADE_BRICK_WALL.asItem());
            itemGroup.add(CultivationModBlocks.MOSSY_JADE_BRICKS.asItem());
            itemGroup.add(CultivationModBlocks.MOSSY_JADE_BRICK_SLAB.asItem());
            itemGroup.add(CultivationModBlocks.MOSSY_JADE_BRICK_STAIRS.asItem());
            itemGroup.add(CultivationModBlocks.MOSSY_JADE_BRICK_WALL.asItem());
        });

        FIRE_SPIRIT_HERB_CROP.setDropItems(CultivationModItems.SPIRIT_HERB_FIRE,CultivationModItems.SPIRIT_HERB_SEEDS_FIRE);
        WATER_SPIRIT_HERB_CROP.setDropItems(CultivationModItems.SPIRIT_HERB_WATER,CultivationModItems.SPIRIT_HERB_SEEDS_WATER);
        AIR_SPIRIT_HERB_CROP.setDropItems(CultivationModItems.SPIRIT_HERB_AIR,CultivationModItems.SPIRIT_HERB_SEEDS_AIR);
        EARTH_SPIRIT_HERB_CROP.setDropItems(CultivationModItems.SPIRIT_HERB_EARTH,CultivationModItems.SPIRIT_HERB_SEEDS_EARTH);
        LIGHTNING_SPIRIT_HERB_CROP.setDropItems(CultivationModItems.SPIRIT_HERB_LIGHTNING,CultivationModItems.SPIRIT_HERB_SEEDS_LIGHTNING);
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

    public static final Block CRACKED_JADE_BRICK_SLAB = register(SlabBlock::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "cracked_jade_brick_slab"
    );

    public static final Block CRACKED_JADE_BRICK_STAIRS = register((Block.Settings settings)-> new StairsBlock(JADE_BRICKS.getDefaultState(), settings),
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "cracked_jade_brick_stairs"
    );

    public static final Block CRACKED_JADE_BRICK_WALL = register(WallBlock::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "cracked_jade_brick_wall"
    );

    public static final Block MOSSY_JADE_BRICKS = register(
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "mossy_jade_bricks"
    );

    public static final Block MOSSY_JADE_BRICK_SLAB = register(SlabBlock::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "mossy_jade_brick_slab"
    );

    public static final Block MOSSY_JADE_BRICK_STAIRS = register((Block.Settings settings)-> new StairsBlock(JADE_BRICKS.getDefaultState(), settings),
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "mossy_jade_brick_stairs"
    );

    public static final Block MOSSY_JADE_BRICK_WALL = register(WallBlock::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "mossy_jade_brick_wall"
    );

    public static final JadeCauldron JADE_CAULDRON = register(JadeCauldron::new,
            AbstractBlock.Settings.copy(JADE_BLOCK),
            "jade_cauldron"
    );

    public static final SpiritHerb FIRE_SPIRIT_HERB_CROP = register(SpiritHerb::new,
            Block.Settings.create().nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).mapColor(MapColor.RED),
            "fire_spirit_herb_crop"
    );

    public static final SpiritHerb WATER_SPIRIT_HERB_CROP = register(SpiritHerb::new,
            Block.Settings.create().nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).mapColor(MapColor.BLUE),
            "water_spirit_herb_crop"
    );

    public static final SpiritHerb AIR_SPIRIT_HERB_CROP = register(SpiritHerb::new,
            Block.Settings.create().nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).mapColor(MapColor.YELLOW),
            "air_spirit_herb_crop"
    );

    public static final SpiritHerb EARTH_SPIRIT_HERB_CROP = register(SpiritHerb::new,
            Block.Settings.create().nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).mapColor(MapColor.BROWN),
            "earth_spirit_herb_crop"
    );

    public static final SpiritHerb LIGHTNING_SPIRIT_HERB_CROP = register(SpiritHerb::new,
            Block.Settings.create().nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).mapColor(MapColor.PURPLE),
            "lightning_spirit_herb_crop"
    );

}
