package com.cultivation_mod.items;

import com.cultivation_mod.CultivationMod;
import com.cultivation_mod.CultivationModComponents;
import com.cultivation_mod.CustomConsumeEffect;
import com.cultivation_mod.cultivation_setup.PlayerCultivationAttachments;
import com.cultivation_mod.element_setup.PlayerElementAttachments;
import com.cultivation_mod.element_setup.PlayerElements;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class RootRegrowthElixir extends UnaspectedQiItem{
    public RootRegrowthElixir(Settings settings) {
        super(settings.component(DataComponentTypes.CONSUMABLE, ConsumableComponents.food()
                .consumeEffect(CustomConsumeEffect.createConsumeEffect("root_regrowth",
                        (world, stack, user) -> {
                            consumeAction(world, user, stack);
                            return true;
                        }
                ))
                .finishSound(SoundEvents.ITEM_TRIDENT_THUNDER)
                .build()));
    }

    public static void consumeAction(World world, LivingEntity user, ItemStack stack) {
        PlayerElements elements = PlayerElementAttachments.createRandomInitElements((PlayerEntity) user);
        PlayerElementAttachments.setCultivationElements(user,elements);
        CultivationMod.LOGGER.info(""+elements);

        PlayerCultivationAttachments.setRealm(user,0);
        PlayerCultivationAttachments.setQi(user,0);

        if(stack.get(CultivationModComponents.ITEM_QI) != null) {
            int storedQi = stack.get(CultivationModComponents.ITEM_QI);
            int startQi = PlayerCultivationAttachments.getQi(user);
            PlayerCultivationAttachments.setQi(user,storedQi+startQi);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.cultivation_mod.root_regrowth_elixir.info"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
