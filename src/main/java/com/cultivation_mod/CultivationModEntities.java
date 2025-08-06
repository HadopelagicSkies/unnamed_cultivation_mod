package com.cultivation_mod;

import com.cultivation_mod.entity.projectiles.SwordSlashProjectile;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class CultivationModEntities {
    public static void initialize(){


        register(SWORD_SLASH_PROJECTILE,"sword_slash_projectile",false);
    }

    public static final EntityType<SwordSlashProjectile> SWORD_SLASH_PROJECTILE = EntityType.Builder.create(SwordSlashProjectile::new, SpawnGroup.MISC)
            .dropsNothing()
            .dimensions(2,0.25F)
            .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CultivationMod.MOD_ID, "sword_slash_projectile")));

    public static <T extends Entity> EntityType<T> register(EntityType<T> entityType, String name, boolean shouldEgg) {
        Identifier id = Identifier.of(CultivationMod.MOD_ID, name);
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, id);
        EntityType<T> entity = Registry.register(Registries.ENTITY_TYPE,key,entityType);

        if(shouldEgg) {
            Identifier eggId = Identifier.of(CultivationMod.MOD_ID, name + "_spawn_egg");
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, eggId);
            Item spawnEggItem = new SpawnEggItem((EntityType<? extends MobEntity>) entityType, new Item.Settings().registryKey(itemKey));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register((itemGroup) -> {
                itemGroup.add(spawnEggItem);
            });
            Registry.register(Registries.ITEM, itemKey, spawnEggItem);
        }
        return entity;
    }

    public static void addSpawning(EntityType<?> entityType, RegistryKey<Biome> biomeKey, int weight, int minGroupSize, int maxGroupSize){
        BiomeModifications.addSpawn(context -> context.getBiomeKey().equals(biomeKey),entityType.getSpawnGroup(),entityType,weight,minGroupSize,maxGroupSize);
    }
}
