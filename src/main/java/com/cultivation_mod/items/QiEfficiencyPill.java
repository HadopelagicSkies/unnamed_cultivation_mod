package com.cultivation_mod.items;

import com.cultivation_mod.CultivationModComponents;
import com.cultivation_mod.CultivationModEffects;
import com.cultivation_mod.CustomConsumeEffect;
import com.cultivation_mod.cultivation_setup.PlayerCultivationAttachments;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class QiEfficiencyPill extends UnaspectedQiItem  {
    public QiEfficiencyPill(Settings settings) {
        super(settings.component(DataComponentTypes.CONSUMABLE,ConsumableComponents.food()
                .consumeEffect(CustomConsumeEffect.createConsumeEffect("qi_efficiency",
                        (world, stack, user) -> {
                            consumeAction(world, user, stack);
                            return true;
                        }
                )).build()));
    }

    public static void consumeAction(World world, LivingEntity user, ItemStack stack) {
        if(stack.get(CultivationModComponents.ITEM_QI) != null) {
            int storedQi = stack.get(CultivationModComponents.ITEM_QI);
            user.addStatusEffect(new StatusEffectInstance(CultivationModEffects.QI_EFFICIENCY,6000,storedQi/100));
            int startQi = PlayerCultivationAttachments.getQi(user);
            PlayerCultivationAttachments.setQi(user,storedQi+startQi);
        }
        else
            user.addStatusEffect(new StatusEffectInstance(CultivationModEffects.QI_EFFICIENCY,6000,0));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.cultivation_mod.qi_efficiency_pill.info"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
