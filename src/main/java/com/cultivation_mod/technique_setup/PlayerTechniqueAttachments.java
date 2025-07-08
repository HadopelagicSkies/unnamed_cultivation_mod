package com.cultivation_mod.technique_setup;


import com.cultivation_mod.CultivationMod;
import com.cultivation_mod.cultivation_setup.PlayerCultivation;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class PlayerTechniqueAttachments {

    public static final AttachmentType<Map<String,Technique>> PLAYER_TECHNIQUES = AttachmentRegistry.create(Identifier.of(CultivationMod.MOD_ID, "player_techniques"), infoBuilder ->
            infoBuilder.initializer(HashMap::new)
                    .persistent(Codec.unboundedMap(Codec.STRING,Technique.TECHNIQUE_CODEC))
                    .copyOnDeath()
                    .syncWith(PacketCodecs.map(HashMap::new,PacketCodecs.STRING,Technique.TECHNIQUE_PACKET_CODEC), AttachmentSyncPredicate.targetOnly()));

    public static void initialize(){};

    public static Map<String,Technique> getPlayerTechniques(AttachmentTarget target){
        return target.getAttached(PLAYER_TECHNIQUES);
    }

    public static void setPlayerTechniques(AttachmentTarget target, Map<String,Technique> techniqueMap){
        target.setAttached(PLAYER_TECHNIQUES,techniqueMap);
    }

    public static Technique getTechnique(AttachmentTarget target, String techniqueKey){
        return target.getAttached(PLAYER_TECHNIQUES).get(techniqueKey);
    }

    public static void setTechnique(AttachmentTarget target, String techniqueKey, Technique technique){
        Map<String, Technique> newMap = new HashMap<>(target.getAttached(PLAYER_TECHNIQUES));
        newMap.put(techniqueKey,technique);
        target.setAttached(PLAYER_TECHNIQUES,newMap);
    }

    public static int getMastery(AttachmentTarget target, String techniqueKey){
        return target.getAttached(PLAYER_TECHNIQUES).get(techniqueKey).mastery();
    }

    public static void setMastery(AttachmentTarget target, String techniqueKey, int mastery){
        Map<String, Technique> newMap = new HashMap<>(target.getAttached(PLAYER_TECHNIQUES));
        Technique oldTechnique = newMap.get(techniqueKey);
        Technique newTechnique = new Technique(oldTechnique.id(), oldTechnique.nameParts(), oldTechnique.slot(), mastery, oldTechnique.cost(), oldTechnique.power(), oldTechnique.range(), oldTechnique.modifiers());
        newMap.put(techniqueKey,newTechnique);
        target.setAttached(PLAYER_TECHNIQUES,newMap);
    }


    //standalone
    public static RegisteredTechnique getRegisteredTechnique(AttachmentTarget target, String techniqueKey){
        return Technique.registeredTechniques.get(target.getAttached(PLAYER_TECHNIQUES).get(techniqueKey).id());
    }





}
