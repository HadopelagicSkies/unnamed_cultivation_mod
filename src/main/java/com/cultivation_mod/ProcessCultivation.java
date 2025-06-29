package com.cultivation_mod;

import com.cultivation_mod.cultivation_setup.PlayerCultivationAttatchments;
import net.minecraft.entity.player.PlayerEntity;

public class ProcessCultivation {
    public static void processCultivation(PlayerEntity player, String pattern){
        CultivationMod.LOGGER.info("Recieved: " + player.getName() + ", " + pattern);

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






    }

    public static void rollMeridianClearing(PlayerEntity player, String key){
        if(player.getRandom().nextBetween(0,100) <= 100){ // odds of clearing
            int progress = PlayerCultivationAttatchments.getSpecificMeridianProgress(player,key);
            progress += player.getRandom().nextBetween(5,15); //amount cleared, add modifier stuff here
            if(progress > 100)
                progress=100;
            PlayerCultivationAttatchments.setSpecificMeridianProgress(player,key,progress);
        }
    }
}
