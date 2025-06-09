package com.cultivation_mod;

import com.cultivation_mod.element_setup.AxisElements;
import com.cultivation_mod.items.AspectedQiItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
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
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_FIRE);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_WATER);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_AIR);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_EARTH);
                    itemGroup.add(CultivationModItems.SPIRIT_STONE_LIGHTNING);
                });
    }

    private static <T extends Item> T register(Function<Item.Settings, T> constructor, Item.Settings itemSettings, String name) {
        Identifier id = Identifier.of(CultivationMod.MOD_ID, name);

        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item.Settings settings = itemSettings.registryKey(key);

        return Registry.register(Registries.ITEM, key, constructor.apply(settings));
    }

    private static Item register(Item.Settings itemSettings, String name) {
        return register(Item::new,itemSettings,name);
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

}
