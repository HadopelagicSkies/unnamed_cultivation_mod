package com.cultivation_mod;

import com.cultivation_mod.element_setup.AxisElements;
import com.cultivation_mod.items.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.Map;
import java.util.function.Function;

public class CultivationModItems {
    public static final RegistryKey<ItemGroup> CULTIVATION_MOD_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(CultivationMod.MOD_ID, "item_group"));
    public static final ItemGroup CULTIVATION_MOD_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(CultivationModItems.SPIRIT_HERB_FIRE))
            .displayName(Text.translatable("itemGroup.cultivation_mod"))
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, CULTIVATION_MOD_GROUP_KEY, CULTIVATION_MOD_GROUP);
        ItemGroupEvents.modifyEntriesEvent(CULTIVATION_MOD_GROUP_KEY)
                .register((itemGroup) -> {
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_FIRE);
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_WATER);
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_AIR);
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_EARTH);
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_LIGHTNING);

                    itemGroup.add(CultivationModItems.SPIRIT_HERB_SEEDS_FIRE);
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_SEEDS_WATER);
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_SEEDS_AIR);
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_SEEDS_EARTH);
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_SEEDS_LIGHTNING);

                    itemGroup.add(CultivationModItems.SPIRIT_STONE_FIRE);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_WATER);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_AIR);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_EARTH);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_LIGHTNING);

                    itemGroup.add(CultivationModItems.QI_EFFICIENCY_PILL);
                    itemGroup.add(CultivationModItems.ROOT_REGROWTH_ELIXIR);
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_MASH);

                    itemGroup.add(CultivationModItems.COMMON_TECHNIQUE_BOOK);
                    itemGroup.add(CultivationModItems.UNCOMMON_TECHNIQUE_BOOK);
                    itemGroup.add(CultivationModItems.RARE_TECHNIQUE_BOOK);
                    itemGroup.add(CultivationModItems.EPIC_TECHNIQUE_BOOK);

                });
    }



    private static <T extends Item> T register(Function<Item.Settings, T> constructor, Item.Settings itemSettings, String name) {
        Identifier id = Identifier.of(CultivationMod.MOD_ID, name);

        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item.Settings settings = itemSettings.registryKey(key);
        Item item = constructor.apply(settings);
        T registeredItem = (T) Registry.register(Registries.ITEM, key, item);
        if (registeredItem instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, blockItem);
        }
        return registeredItem;
    }

    private static Item register(Item.Settings itemSettings, String name) {
        return register(Item::new,itemSettings,name);
    }

    private static Function<Item.Settings, BlockItem> createBlockItem(Block block) {
        return (settings) -> new BlockItem (block, settings.useItemPrefixedTranslationKey());
    }

    private static Function<Item.Settings, AspectedQiBlockItem> createAspectedQiBlockItem(Block block) {
        return (settings) -> new AspectedQiBlockItem (block, settings.useItemPrefixedTranslationKey());
    }

    private static Function<Item.Settings, UnaspectedQiBlockItem> createUnaspectedQiBlockItem(Block block) {
        return (settings) -> new UnaspectedQiBlockItem (block, settings.useItemPrefixedTranslationKey());
    }

    public static final AspectedQiItem SPIRIT_HERB_FIRE = register(AspectedQiItem::new,
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.FIRE,50)),
            "spirit_herb_fire"
    );
    public static final AspectedQiItem SPIRIT_HERB_WATER = register(AspectedQiItem::new,
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.WATER,50)),
            "spirit_herb_water"
    );
    public static final AspectedQiItem SPIRIT_HERB_AIR = register(AspectedQiItem::new,
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.AIR,50)),
            "spirit_herb_air"
    );
    public static final AspectedQiItem SPIRIT_HERB_EARTH = register(AspectedQiItem::new,
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.EARTH,50)),
            "spirit_herb_earth"
    );
    public static final AspectedQiItem SPIRIT_HERB_LIGHTNING = register(AspectedQiItem::new,
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.LIGHTNING,50)),
            "spirit_herb_lightning"
    );

    public static final AspectedQiBlockItem SPIRIT_HERB_SEEDS_FIRE = register(createAspectedQiBlockItem(CultivationModBlocks.FIRE_SPIRIT_HERB_CROP),
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.FIRE,25)),
            "spirit_herb_seeds_fire"
    );
    public static final AspectedQiBlockItem SPIRIT_HERB_SEEDS_WATER = register(createAspectedQiBlockItem(CultivationModBlocks.WATER_SPIRIT_HERB_CROP),
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.WATER,25)),
            "spirit_herb_seeds_water"
    );
    public static final AspectedQiBlockItem SPIRIT_HERB_SEEDS_AIR = register(createAspectedQiBlockItem(CultivationModBlocks.AIR_SPIRIT_HERB_CROP),
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.AIR,25)),
            "spirit_herb_seeds_air"
    );
    public static final AspectedQiBlockItem SPIRIT_HERB_SEEDS_EARTH = register(createAspectedQiBlockItem(CultivationModBlocks.EARTH_SPIRIT_HERB_CROP),
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.EARTH,25)),
            "spirit_herb_seeds_earth"
    );
    public static final AspectedQiBlockItem SPIRIT_HERB_SEEDS_LIGHTNING = register(createAspectedQiBlockItem(CultivationModBlocks.LIGHTNING_SPIRIT_HERB_CROP),
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.LIGHTNING,25)),
            "spirit_herb_seeds_lightning"
    );

    public static final AspectedQiItem SPIRIT_STONE_FIRE = register(AspectedQiItem::new,
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.FIRE,150)),
            "spirit_stone_fire"
    );
    public static final AspectedQiItem SPIRIT_STONE_WATER = register(AspectedQiItem::new,
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.WATER,150)),
            "spirit_stone_water"
    );
    public static final AspectedQiItem SPIRIT_STONE_AIR = register(AspectedQiItem::new,
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.AIR,150)),
            "spirit_stone_air"
    );
    public static final AspectedQiItem SPIRIT_STONE_EARTH = register(AspectedQiItem::new,
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.EARTH,150)),
            "spirit_stone_earth"
    );
    public static final AspectedQiItem SPIRIT_STONE_LIGHTNING = register(AspectedQiItem::new,
            new Item.Settings().component(CultivationModComponents.ITEM_ELEMENTS, Map.of(AxisElements.LIGHTNING,150)),
            "spirit_stone_lightning"
    );

    public static final QiEfficiencyPill QI_EFFICIENCY_PILL = register(QiEfficiencyPill::new,
            new Item.Settings().component(CultivationModComponents.ITEM_QI, 100),
            "qi_efficiency_pill"
    );

    public static final RootRegrowthElixir ROOT_REGROWTH_ELIXIR = register(RootRegrowthElixir::new,
            new Item.Settings().component(CultivationModComponents.ITEM_QI, 100),
            "root_regrowth_elixir"
    );

    public static final SpiritHerbMash SPIRIT_HERB_MASH = register(SpiritHerbMash::new,
            new Item.Settings(),
            "spirit_herb_mash"
    );

    public static TechniqueBook COMMON_TECHNIQUE_BOOK = register(TechniqueBook::new,
            new Item.Settings().rarity(Rarity.COMMON),
            "common_technique_book"
    );

    public static TechniqueBook UNCOMMON_TECHNIQUE_BOOK = register(TechniqueBook::new,
            new Item.Settings().rarity(Rarity.UNCOMMON),
            "uncommon_technique_book"
    );

    public static TechniqueBook RARE_TECHNIQUE_BOOK = register(TechniqueBook::new,
            new Item.Settings().rarity(Rarity.RARE),
            "rare_technique_book"
    );

    public static TechniqueBook EPIC_TECHNIQUE_BOOK = register(TechniqueBook::new,
            new Item.Settings().rarity(Rarity.EPIC),
            "epic_technique_book"
    );
}
