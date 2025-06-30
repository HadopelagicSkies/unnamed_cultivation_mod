package com.cultivation_mod;

import com.cultivation_mod.element_setup.AxisElements;
import com.cultivation_mod.items.AspectedQiItem;
import com.cultivation_mod.items.QiEfficiencyPill;
import com.cultivation_mod.items.RootRegrowthElixir;
import com.cultivation_mod.items.SpiritHerbMash;
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
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_SEEDS);

                    itemGroup.add(CultivationModItems.SPIRIT_STONE_FIRE);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_WATER);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_AIR);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_EARTH);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_LIGHTNING);

                    itemGroup.add(CultivationModItems.QI_EFFICIENCY_PILL);
                    itemGroup.add(CultivationModItems.ROOT_REGROWTH_ELIXIR);
                    itemGroup.add(CultivationModItems.SPIRIT_HERB_MASH);
                });
    }



    private static <T extends Item> T register(Function<Item.Settings, T> constructor, Item.Settings itemSettings, Function<Item.Settings, Item> function , String name) {
        Identifier id = Identifier.of(CultivationMod.MOD_ID, name);

        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item.Settings settings = itemSettings.registryKey(key);
        Item item;
        if(function != null) {
            item = function.apply(settings);
            if (item instanceof BlockItem blockItem) {
                blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
            }
        }
        else
            item = constructor.apply(settings);
        return (T) Registry.register(Registries.ITEM, key, item);
    }

    private static <T extends Item> T register(Function<Item.Settings, T> constructor, Item.Settings itemSettings, String name) {
        return register(constructor,itemSettings,null,name);
    }
    private static Item register(Item.Settings itemSettings, String name) {
        return register(Item::new,itemSettings,name);
    }

    private static Function<Item.Settings, Item> createBlockItemWithUniqueName(Block block) {
        return (settings) -> new BlockItem(block, settings.useItemPrefixedTranslationKey());
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

    public static final Item SPIRIT_HERB_SEEDS = register(Item::new,
            new Item.Settings(),
            createBlockItemWithUniqueName(CultivationModBlocks.SPIRIT_HERB),
            "spirit_herb_seeds"
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

}
