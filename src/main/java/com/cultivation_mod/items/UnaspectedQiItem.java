package com.cultivation_mod.items;

import com.cultivation_mod.CultivationModComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class UnaspectedQiItem extends Item {
    public UnaspectedQiItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(stack.contains(CultivationModComponents.ITEM_QI)) {
            int qi = stack.get(CultivationModComponents.ITEM_QI);
            if(qi > 0)
                tooltip.add(Text.translatable("item.cultivation_mod.unaspected_item.info", qi));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
