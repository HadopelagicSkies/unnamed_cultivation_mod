package com.cultivation_mod;

import com.cultivation_mod.cultivation_setup.PlayerCultivation;
import com.cultivation_mod.cultivation_setup.PlayerCultivationAttachments;
import com.cultivation_mod.element_setup.AxisElements;
import com.cultivation_mod.element_setup.PlayerElementAttachments;
import com.cultivation_mod.element_setup.PlayerElements;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<AxisElements,Integer> meridianElementValues = new HashMap<>();
        for(AxisElements meridianElement:AxisElements.values())
            meridianElementValues.put(meridianElement,0);

        //dantian
        if(Integer.parseInt(String.valueOf(pattern.charAt(0))) != 0){
            rollMeridianClearing(player,"stomach");
            rollMeridianClearing(player,"gut");
            if(pattern.charAt(0) == '1'){

            } else {

            }
        }
        //stomach
        if(Integer.parseInt(String.valueOf(pattern.charAt(1))) != 0){
            rollMeridianClearing(player,"heart");
            if(pattern.charAt(1) == '1'){

            } else {

            }
        }
        //heart
        if(Integer.parseInt(String.valueOf(pattern.charAt(2))) != 0){
            rollMeridianClearing(player,"shoulderL");
            rollMeridianClearing(player,"shoulderR");
            rollMeridianClearing(player,"head");
            if(pattern.charAt(2) == '1'){

            } else {

            }
        }
        //shoulderL
        if(Integer.parseInt(String.valueOf(pattern.charAt(3))) != 0){
            rollMeridianClearing(player,"armL");
            if(pattern.charAt(3) == '1'){

            } else {

            }
        }
        //shoulderR
        if(Integer.parseInt(String.valueOf(pattern.charAt(4))) != 0){
            rollMeridianClearing(player,"armR");
            if(pattern.charAt(4) == '1'){

            } else {

            }
        }
        //armL
        if(Integer.parseInt(String.valueOf(pattern.charAt(5))) != 0){
            if(pattern.charAt(5) == '1'){

            } else {

            }
        }
        //armR
        if(Integer.parseInt(String.valueOf(pattern.charAt(6))) != 0){
            if(pattern.charAt(6) == '1'){

            } else {

            }
        }
        //head
        if(Integer.parseInt(String.valueOf(pattern.charAt(7))) != 0){
            if(pattern.charAt(7) == '1'){

            } else {

            }
        }
        //gut
        if(Integer.parseInt(String.valueOf(pattern.charAt(8))) != 0){
            rollMeridianClearing(player,"legL");
            rollMeridianClearing(player,"legR");
            if(pattern.charAt(8) == '1'){

            } else {

            }
        }
        //legL
        if(Integer.parseInt(String.valueOf(pattern.charAt(9))) != 0){
            if(pattern.charAt(9) == '1'){

            } else {

            }
        }
        //legR
        if(Integer.parseInt(String.valueOf(pattern.charAt(10))) != 0){
            if(pattern.charAt(10) == '1'){

            } else {

            }
        }



        double cultivationEfficiency = (double) FengShuiCalc.calculateFengShui(player.getWorld(), player.getBlockPos()) / 1200;

        List<AxisElements> biomeElements = getBiomeElements(player.getWorld().getBiome(player.getBlockPos()));
        cultivationEfficiency += 0.5 * (int) biomeElements.stream().filter((biomeElement)-> elements.favoredElements().contains(biomeElement)).count();

        for(AxisElements biomeElement:biomeElements){
            if(elements.favoredElements().contains(biomeElement))
                rollElementIncrease(player,biomeElement, cultivationEfficiency+1);
            else
                rollElementIncrease(player,biomeElement, cultivationEfficiency);
        }

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

    public static void rollElementIncrease(PlayerEntity player, AxisElements element, double efficiency){
        if(player.getRandom().nextBetween(0,100) <= 100){ // odds of adding
            int level = PlayerElementAttachments.getElementLevel(player,element);
            level += (int) (player.getRandom().nextBetween(5,15) * efficiency);  //amount added, add modifier stuff here
            PlayerElementAttachments.setElementLevel(player,element,level);
        }
    }

    public static int getRealmMaxQi(int realm){
        switch (realm){
            case(0) -> {return 1000;}
            case(1) -> {return 5000;}
            case(2) -> {return 10000;}
            case(3) -> {return 50000;}
            case(4) -> {return 100000;}
            case(5) -> {return 500000;}
            case(6) -> {return 1000000;}
            default -> {return 0;}
        }
        //return 1000 * Math.pow(2,realm);      maybe something this instead? idk
    }

    public static int getLocationQiLevel(BlockPos pos){
        return 100; // the big thingy map here
    }

    public static List<AxisElements> getBiomeElements(RegistryEntry<Biome> biome){
        List<AxisElements> elementList = new ArrayList<>();
        if (biome.isIn(CultivationModTags.FIRE_BIOME)) elementList.add(AxisElements.FIRE);
        if (biome.isIn(CultivationModTags.WATER_BIOME)) elementList.add(AxisElements.WATER);
        if (biome.isIn(CultivationModTags.AIR_BIOME)) elementList.add(AxisElements.AIR);
        if (biome.isIn(CultivationModTags.EARTH_BIOME)) elementList.add(AxisElements.EARTH);
        if (biome.isIn(CultivationModTags.LIGHTNING_BIOME)) elementList.add(AxisElements.LIGHTNING);
        if (biome.isIn(CultivationModTags.YIN_BIOME)) elementList.add(AxisElements.YIN);
        if (biome.isIn(CultivationModTags.YANG_BIOME)) elementList.add(AxisElements.YANG);
        return elementList;
    }
}
