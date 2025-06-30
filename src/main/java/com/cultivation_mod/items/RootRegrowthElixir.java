package com.cultivation_mod.items;

import com.cultivation_mod.CultivationMod;
import com.cultivation_mod.CultivationModComponents;
import com.cultivation_mod.cultivation_setup.PlayerCultivationAttatchments;
import com.cultivation_mod.element_setup.PlayerElementAttachments;
import com.cultivation_mod.element_setup.PlayerElements;
import net.minecraft.component.type.Consumable;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class RootRegrowthElixir extends UnaspectedQiItem implements Consumable {
    public RootRegrowthElixir(Settings settings) {
        super(settings.food(new FoodComponent.Builder().alwaysEdible().build(), ConsumableComponents.food().build()));
    }

    @Override
    public void onConsume(World world, LivingEntity user, ItemStack stack, ConsumableComponent consumable) {
        PlayerElements elements = PlayerElementAttachments.createRandomInitElements((PlayerEntity) user);
        PlayerElementAttachments.setCultivationElements(user,elements);
        CultivationMod.LOGGER.info(""+elements);

        PlayerCultivationAttatchments.setRealm(user,0);
        PlayerCultivationAttatchments.setQi(user,0);

        if(stack.get(CultivationModComponents.ITEM_QI) != null) {
            int storedQi = stack.get(CultivationModComponents.ITEM_QI);
            PlayerCultivationAttatchments.getQi(user);
            PlayerCultivationAttatchments.setQi(user,storedQi);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.cultivation_mod.root_regrowth_elixir.info"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
