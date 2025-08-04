package com.cultivation_mod.items;

import com.cultivation_mod.CultivationModComponents;
import com.cultivation_mod.CustomConsumeEffect;
import com.cultivation_mod.technique_setup.PlayerTechniqueAttachments;
import com.cultivation_mod.technique_setup.Technique;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;

public class TechniqueBook extends Item {
    public TechniqueBook(Settings settings) {
        super(settings.component(DataComponentTypes.CONSUMABLE,ConsumableComponent.builder().consumeSeconds(3.2F).useAction(UseAction.NONE)
                .consumeEffect(CustomConsumeEffect.createConsumeEffect("learn_technique",
                        (world, stack, user) -> {
                            Technique technique = stack.get(CultivationModComponents.BOOK_TECHNIQUE);
                            if (technique != null) {
                                PlayerTechniqueAttachments.setTechnique(user, Technique.registeredTechniques.get(technique.id()).getSlot(), technique);
                                return true;
                            }
                            else {
                                stack.set(CultivationModComponents.BOOK_TECHNIQUE,Technique.createRandomTechnique(stack,user));
                                return false;
                            }
                        }
                ))
                .finishSound(SoundEvents.ITEM_TRIDENT_THUNDER)
                .build()));
    }



    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Technique technique = stack.get(CultivationModComponents.BOOK_TECHNIQUE);
        if (technique != null) {
            MutableText nameText = Text.empty();
            for(String namePart: technique.nameParts()){
                nameText.append(Text.translatable(namePart));
            }
            tooltip.add(nameText);
            tooltip.add(Text.translatable(Technique.registeredTechniques.get(technique.id()).getDesc()));
            tooltip.add(Text.translatable("item.cultivation_mod.technique.element", Technique.registeredTechniques.get(technique.id()).getElement()));
            tooltip.add(Text.translatable("item.cultivation_mod.technique.cost", technique.cost()));
            tooltip.add(Text.translatable("item.cultivation_mod.technique.stats", technique.power(),technique.range()));
            for (String modifier : technique.modifiers()){
                tooltip.add(Text.translatable(Technique.registeredTechniques.get(technique.id()).getModifierDesc(modifier)));
            }
        }
        else{
            tooltip.add(Text.translatable("item.cultivation_mod.technique.unidentified_technique"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
