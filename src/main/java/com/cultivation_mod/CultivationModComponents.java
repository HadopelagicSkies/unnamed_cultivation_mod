package com.cultivation_mod;

import com.cultivation_mod.element_setup.AxisElements;
import com.cultivation_mod.technique_setup.Technique;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Map;

public class CultivationModComponents {
    protected static void initialize() {
        CultivationMod.LOGGER.info("Registering {} components", CultivationMod.MOD_ID);
    }

    public static final ComponentType<Map<AxisElements, Integer>> ITEM_ELEMENTS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(CultivationMod.MOD_ID, "item_elements"),
            ComponentType.<Map<AxisElements, Integer>>builder().codec(AxisElements.ELEMENT_MAP_CODEC).build()
    );

    public static final ComponentType<Integer> ITEM_QI = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(CultivationMod.MOD_ID, "item_qi"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Technique> BOOK_TECHNIQUE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(CultivationMod.MOD_ID, "book_technique"),
            ComponentType.<Technique>builder().codec(Technique.TECHNIQUE_CODEC).build()
    );
}
