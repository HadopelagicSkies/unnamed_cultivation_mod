package com.cultivation_mod;

import com.cultivation_mod.status_effects.DantianSenseEffect;
import com.cultivation_mod.status_effects.QiEfficiencyEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class CultivationModEffects {
    public static RegistryEntry.Reference<StatusEffect> DANTIAN_SENSE = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(CultivationMod.MOD_ID,"dantian_sense"),new DantianSenseEffect());
    public static RegistryEntry.Reference<StatusEffect> QI_EFFICIENCY = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(CultivationMod.MOD_ID,"qi_efficiency"),new QiEfficiencyEffect());

    public static void initialize(){}
}
