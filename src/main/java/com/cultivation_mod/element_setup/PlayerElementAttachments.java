package com.cultivation_mod.element_setup;

import com.cultivation_mod.CultivationMod;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerElementAttachments {
    public static final AttachmentType<PlayerElements> CULTIVATION_ELEMENTS = AttachmentRegistry.create(Identifier.of(CultivationMod.MOD_ID, "cultivation_elements"), infoBuilder ->
            infoBuilder.initializer(() -> new PlayerElements(new HashMap<>(),new ArrayList<>()))
                    .persistent(PlayerElements.PLAYER_ELEMENTS_CODEC)
                    .copyOnDeath()
                    .syncWith(PlayerElements.PLAYER_ELEMENTS_PACKET_CODEC,AttachmentSyncPredicate.targetOnly()));

    public static void setCultivationElements(AttachmentTarget target, PlayerElements elements) {
        target.setAttached(CULTIVATION_ELEMENTS,elements);
    }
    public static PlayerElements getCultivationElements(AttachmentTarget target) {
        return target.getAttached(CULTIVATION_ELEMENTS);
    }

    public static void setElementMap(AttachmentTarget target, Map<AxisElements, Integer> elementMap) {
        target.setAttached(CULTIVATION_ELEMENTS,new PlayerElements(elementMap,getFavoredElements(target)));
    }
    public static Map<AxisElements, Integer> getElementMap(AttachmentTarget target) {
        return target.getAttached(CULTIVATION_ELEMENTS).playerElements();
    }

    public static void setFavoredElements(AttachmentTarget target, List<AxisElements> favored) {
        target.setAttached(CULTIVATION_ELEMENTS,new PlayerElements(getElementMap(target),favored));
    }
    public static List<AxisElements> getFavoredElements(AttachmentTarget target) {
        return target.getAttached(CULTIVATION_ELEMENTS).favoredElements();
    }

    public static void setElementLevel(AttachmentTarget target, AxisElements element, int level) {
        Map<AxisElements, Integer> newMap = new HashMap<>(getElementMap(target));
        newMap.put(element,level);
        target.setAttached(CULTIVATION_ELEMENTS,new PlayerElements(newMap,getFavoredElements(target)));
    }
    public static int getElementLevel(AttachmentTarget target,AxisElements element) {
        return target.getAttached(CULTIVATION_ELEMENTS).playerElements().get(element);
    }

    public static PlayerElements createRandomInitElements(PlayerEntity entity) {
        Map<AxisElements, Integer> elementLevels = new HashMap<AxisElements, Integer>();
        List<AxisElements> favored = new ArrayList<>();

        if (entity.getRandom().nextBoolean())
            favored.add(AxisElements.YIN);
        else
            favored.add(AxisElements.YANG);

        switch (entity.getRandom().nextBetween(1, 5)) {
            case 1 -> favored.add(AxisElements.FIRE);
            case 2 -> favored.add(AxisElements.WATER);
            case 3 -> favored.add(AxisElements.AIR);
            case 4 -> favored.add(AxisElements.EARTH);
            case 5 -> favored.add(AxisElements.LIGHTNING);
        }
        if (entity.getRandom().nextBetween(0, 100) < 50) {
            switch (entity.getRandom().nextBetween(1, 5)) {
                case 1 -> favored.add(AxisElements.FIRE);
                case 2 -> favored.add(AxisElements.WATER);
                case 3 -> favored.add(AxisElements.AIR);
                case 4 -> favored.add(AxisElements.EARTH);
                case 5 -> favored.add(AxisElements.LIGHTNING);
            }
        }
        if (entity.getRandom().nextBetween(0, 100) < 25) {
            switch (entity.getRandom().nextBetween(1, 5)) {
                case 1 -> favored.add(AxisElements.FIRE);
                case 2 -> favored.add(AxisElements.WATER);
                case 3 -> favored.add(AxisElements.AIR);
                case 4 -> favored.add(AxisElements.EARTH);
                case 5 -> favored.add(AxisElements.LIGHTNING);
            }
        }

        for (AxisElements element : AxisElements.values()) {
            if(favored.contains(element))
                elementLevels.put(element,50);
            else
                elementLevels.put(element,0);
        }

        return new PlayerElements(elementLevels, favored);
    }
}
