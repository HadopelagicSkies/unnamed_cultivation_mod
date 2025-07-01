package com.cultivation_mod;

import com.cultivation_mod.cultivation_setup.PlayerCultivation;
import com.cultivation_mod.cultivation_setup.PlayerCultivationAttachments;
import com.cultivation_mod.element_setup.PlayerElementAttachments;
import com.cultivation_mod.element_setup.PlayerElements;
import net.minecraft.entity.player.PlayerEntity;

public class ProcessCultivation {
    public static void processCultivation(PlayerEntity player, String pattern){
        CultivationMod.LOGGER.info("Recieved: " + player.getName() + ", " + pattern);

        PlayerElements elements = PlayerElementAttachments.getCultivationElements(player);
        PlayerCultivation cultivation = PlayerCultivationAttachments.getCultivation(player);

        if(cultivation.realm() < 0 && player.hasStatusEffect(CultivationModEffects.DANTIAN_SENSE)) {
            PlayerCultivationAttachments.setRealm(player,0);
        }

        CultivationMod.LOGGER.info(""+elements);
        CultivationMod.LOGGER.info(""+cultivation);

        //dantian
        if(Integer.parseInt(String.valueOf(pattern.charAt(0))) != 0){
            rollMeridianClearing(player,"stomach");
            rollMeridianClearing(player,"gut");
        }
        //stomach
        if(Integer.parseInt(String.valueOf(pattern.charAt(1))) != 0){
            rollMeridianClearing(player,"heart");
        }
        //heart
        if(Integer.parseInt(String.valueOf(pattern.charAt(2))) != 0){
            rollMeridianClearing(player,"shoulderL");
            rollMeridianClearing(player,"shoulderR");
            rollMeridianClearing(player,"head");
        }
        //shoulderL
        if(Integer.parseInt(String.valueOf(pattern.charAt(3))) != 0){
            rollMeridianClearing(player,"armL");
        }
        //shoulderR
        if(Integer.parseInt(String.valueOf(pattern.charAt(4))) != 0){
            rollMeridianClearing(player,"armR");
        }
        //gut
        if(Integer.parseInt(String.valueOf(pattern.charAt(8))) != 0){
            rollMeridianClearing(player,"legL");
            rollMeridianClearing(player,"legR");
        }

        elements.playerElements().forEach(((element, integer) -> {
            if(elements.favoredElements().contains(element)){
                PlayerElementAttachments.setElementLevel(player,element,PlayerElementAttachments.getElementLevel(player,element)+player.getRandom().nextBetween(10,50));
            }
        }));

    }

    public static void rollMeridianClearing(PlayerEntity player, String key){
        if(player.getRandom().nextBetween(0,100) <= 100){ // odds of clearing
            int progress = PlayerCultivationAttachments.getSpecificMeridianProgress(player,key);
            progress += player.getRandom().nextBetween(5,15); //amount cleared, add modifier stuff here
            if(progress > 100)
                progress=100;
            PlayerCultivationAttachments.setSpecificMeridianProgress(player,key,progress);
        }
    }
}
