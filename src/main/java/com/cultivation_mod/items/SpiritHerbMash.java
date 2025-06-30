package com.cultivation_mod.items;

import com.cultivation_mod.CultivationMod;
import com.cultivation_mod.CultivationModComponents;
import com.cultivation_mod.CultivationModEffects;
import com.cultivation_mod.cultivation_setup.PlayerCultivationAttatchments;
import com.cultivation_mod.element_setup.PlayerElementAttachments;
import com.cultivation_mod.element_setup.PlayerElements;
import net.minecraft.component.type.Consumable;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.datafixer.fix.FoodToConsumableFix;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class SpiritHerbMash extends Item implements Consumable {
    public static final ConsumableComponent HERB_MASH_CONSUMABLE_COMPONENT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(CultivationModEffects.DANTIAN_SENSE,6000,0), 1.0f))
            .build();
    public static final FoodComponent HERB_MASH_COMPONENT = new FoodComponent.Builder()
            .alwaysEdible()
            .build();
    public SpiritHerbMash(Settings settings) {
        super(settings.food(HERB_MASH_COMPONENT,HERB_MASH_CONSUMABLE_COMPONENT));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.cultivation_mod.spirit_herb_mash.info"));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public void onConsume(World world, LivingEntity user, ItemStack stack, ConsumableComponent consumable) {

    }
}
