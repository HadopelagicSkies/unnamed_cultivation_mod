package com.cultivation_mod.items;

import com.cultivation_mod.CultivationModEffects;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.Consumable;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class SpiritHerbMash extends Item {
    public SpiritHerbMash(Settings settings) {
        super(settings.component(DataComponentTypes.CONSUMABLE, ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(CultivationModEffects.DANTIAN_SENSE,6000,0), 1.0f))
                .build()));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.cultivation_mod.spirit_herb_mash.info"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
