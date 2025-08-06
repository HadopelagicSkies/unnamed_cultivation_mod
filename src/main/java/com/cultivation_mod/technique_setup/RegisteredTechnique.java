package com.cultivation_mod.technique_setup;

import com.cultivation_mod.element_setup.AxisElements;
import com.cultivation_mod.element_setup.CompoundElements;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class RegisteredTechnique {
    public final String id;

    public static void initialize(){
        Technique.registeredTechniques.put("sword_slash",new SwordSlashTechnique("sword_slash"));
    }

    public RegisteredTechnique(String id){
        Technique.registeredTechniques.put(id,this);
        this.id = id;
    }


    public void activeEffect(PlayerEntity player, List<String> nameParts, int mastery, int cost, int power, int range, List<String> modifiers) {

    }

    public void passiveEffect(PlayerEntity player, List<String> nameParts, int mastery, int cost, int power, int range, List<String> modifiers) {

    }

    public void onTickEffect(PlayerEntity player, List<String> nameParts, int mastery, int cost, int power, int range, List<String> modifiers) {

    }



    public AxisElements getAxisElement() {
        return null;
    }
    public CompoundElements getCompoundElement() {
        return null;
    }

    public String getSlot(){
        return "";
    }

    public String getDesc() {
        return "";
    }
    public String getModifierDesc(String modifier) {
        return "";
    }

    public List<String> createNameParts(LivingEntity entity) {
        return new ArrayList<>();
    }
    public List<String> createModifiers(LivingEntity entity) {
        return new ArrayList<>();
    }

}
