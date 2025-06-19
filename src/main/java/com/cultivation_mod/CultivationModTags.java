package com.cultivation_mod;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class CultivationModTags {
    public static final TagKey<Item> SPIRIT_HERB = TagKey.of(RegistryKeys.ITEM, Identifier.of(CultivationMod.MOD_ID,"spirit_herb"));
    public static final TagKey<Item> SPIRIT_STONE = TagKey.of(RegistryKeys.ITEM,Identifier.of(CultivationMod.MOD_ID,"spirit_stone"));

    public static final TagKey<Biome> FIRE_BIOME = TagKey.of(RegistryKeys.BIOME,Identifier.of(CultivationMod.MOD_ID,"fire_biome"));
    public static final TagKey<Biome> WATER_BIOME = TagKey.of(RegistryKeys.BIOME,Identifier.of(CultivationMod.MOD_ID,"water_biome"));
    public static final TagKey<Biome> AIR_BIOME = TagKey.of(RegistryKeys.BIOME,Identifier.of(CultivationMod.MOD_ID,"air_biome"));
    public static final TagKey<Biome> EARTH_BIOME = TagKey.of(RegistryKeys.BIOME,Identifier.of(CultivationMod.MOD_ID,"earth_biome"));
    public static final TagKey<Biome> LIGHTNING_BIOME = TagKey.of(RegistryKeys.BIOME,Identifier.of(CultivationMod.MOD_ID,"lighting_biome"));
    public static final TagKey<Biome> YIN_BIOME = TagKey.of(RegistryKeys.BIOME,Identifier.of(CultivationMod.MOD_ID,"yin_biome"));
    public static final TagKey<Biome> YANG_BIOME = TagKey.of(RegistryKeys.BIOME,Identifier.of(CultivationMod.MOD_ID,"yang_biome"));



    public static void initialize(){}
}
