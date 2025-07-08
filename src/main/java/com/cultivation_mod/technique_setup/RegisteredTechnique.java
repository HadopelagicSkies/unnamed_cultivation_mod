package com.cultivation_mod.technique_setup;

import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class RegisteredTechnique {
    public final String id;
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

}
