package com.cultivation_mod.items;

import com.cultivation_mod.CultivationModComponents;
import com.cultivation_mod.element_setup.AxisElements;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Map;

public class AspectedQiBlockItem extends BlockItem {
    public AspectedQiBlockItem(Block block, Settings settings) {
        super(block,settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(stack.contains(CultivationModComponents.ITEM_ELEMENTS)) {
            Map<AxisElements, Integer> elements = stack.get(CultivationModComponents.ITEM_ELEMENTS);
            if(elements.size() > 1) {
                int totalQi = 0;
                for (int qi : elements.values()) {
                    totalQi += qi;
                }
                tooltip.add(Text.translatable("item.cultivation_mod.aspected_item.info", totalQi));
            }
            elements.forEach(((axisElements, qi) -> {
                if(qi > 0) {
                    Formatting color = Formatting.WHITE;
                    switch (axisElements) {
                        case FIRE -> color = Formatting.RED;
                        case WATER -> color = Formatting.AQUA;
                        case AIR -> color = Formatting.GREEN;
                        case EARTH -> color = Formatting.YELLOW;
                        case LIGHTNING -> color = Formatting.DARK_PURPLE;
                        case YIN -> color = Formatting.WHITE;
                        case YANG -> color = Formatting.DARK_GRAY;
                    }
                    tooltip.add(Text.translatable("item.cultivation_mod.aspected_item.info2", axisElements.getTranslatableName(), qi).formatted(color));
                }
            } ));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
