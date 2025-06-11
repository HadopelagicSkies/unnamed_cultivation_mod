package com.cultivation_mod.items;

import com.cultivation_mod.CultivationModComponents;
import com.cultivation_mod.cultivation_setup.PlayerCultivationAttatchments;
import net.minecraft.component.type.Consumable;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class QiEfficiencyPill extends UnaspectedQiItem implements Consumable {
    public QiEfficiencyPill(Settings settings) {
        super(settings);
    }

    @Override
    public void onConsume(World world, LivingEntity user, ItemStack stack, ConsumableComponent consumable) {
        if(stack.get(CultivationModComponents.ITEM_QI) != null) {
            int storedQi = stack.get(CultivationModComponents.ITEM_QI);
            PlayerCultivationAttatchments.getQi(user);
            PlayerCultivationAttatchments.setQi(user,storedQi);
        }
    }
}
