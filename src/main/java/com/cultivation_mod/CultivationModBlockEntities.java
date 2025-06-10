package com.cultivation_mod;

import com.cultivation_mod.blocks.JadeCauldronEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CultivationModBlockEntities {
    public static void initialize() {
    }

    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(CultivationMod.MOD_ID, path), blockEntityType);
    }

    public static final BlockEntityType<JadeCauldronEntity> JADE_CAULDRON_BLOCK_ENTITY = register(
            "jade_cauldron",
            FabricBlockEntityTypeBuilder.create(JadeCauldronEntity::new, CultivationModBlocks.JADE_CAULDRON).build());

}
