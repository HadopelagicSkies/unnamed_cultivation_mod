package com.cultivation_mod;

import com.cultivation_mod.blocks.JadeCauldron;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class CultivationModBlocks {
    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(CultivationModItems.CULTIVATION_MOD_GROUP_KEY).register((itemGroup) ->
        {
            itemGroup.add(CultivationModBlocks.JADE_BLOCK.asItem());
            itemGroup.add(CultivationModBlocks.JADE_CAULDRON.asItem());
        });
    }

    public static <T extends Block> T register(Function<Block.Settings, T> constructor, Block.Settings blockSettings, String name) {
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
            Block.Settings.create(),
            "jade_block"
    );

    public static final Block JADE_CAULDRON = register(JadeCauldron::new,
            Block.Settings.create(),
            "jade_cauldron"
    );

}
