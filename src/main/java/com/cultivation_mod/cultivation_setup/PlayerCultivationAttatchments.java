package com.cultivation_mod.cultivation_setup;

import com.cultivation_mod.CultivationMod;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class PlayerCultivationAttatchments {

    public static final AttachmentType<PlayerCultivation> PLAYER_CULTIVATION = AttachmentRegistry.create(Identifier.of(CultivationMod.MOD_ID, "player_cultivation"), infoBuilder ->
            infoBuilder.initializer(() -> new PlayerCultivation(-1,0,initMeridians()))
                    .persistent(PlayerCultivation.PLAYER_CULTIVATION_CODEC)
                    .copyOnDeath()
                    .syncWith(PlayerCultivation.PLAYER_CULTIVATION_PACKET_CODEC, AttachmentSyncPredicate.targetOnly()));

    public static Map<String, Integer> initMeridians(){
        Map<String, Integer> meridianMap = new HashMap<>();
        meridianMap.put("head",0);
        meridianMap.put("heart",0);
        meridianMap.put("stomach",0);
        meridianMap.put("gut",0);
        meridianMap.put("shoulderL",0);
        meridianMap.put("shoulderR",0);
        meridianMap.put("armL",0);
        meridianMap.put("armR",0);
        meridianMap.put("legL",0);
        meridianMap.put("legR",0);
        return meridianMap;
    }

    public static PlayerCultivation getCultivation(AttachmentTarget target){
        return target.getAttached(PLAYER_CULTIVATION);
    }

    public static int getRealm(AttachmentTarget target){
        return target.getAttached(PLAYER_CULTIVATION).realm();
    }

    public static int getQi(AttachmentTarget target){
        return target.getAttached(PLAYER_CULTIVATION).qi();
    }

    public static Map<String, Integer> getMeridianProgress(AttachmentTarget target){
        return target.getAttached(PLAYER_CULTIVATION).meridianProgress();
    }

    public static int getSpecificMeridianProgress(AttachmentTarget target, String key){
        return target.getAttached(PLAYER_CULTIVATION).meridianProgress().get(key);
    }

    public static void setCultivation(AttachmentTarget target, PlayerCultivation playerCultivation){
        target.setAttached(PLAYER_CULTIVATION,playerCultivation);
    }

    public static void setRealm(AttachmentTarget target, int realm){
        PlayerCultivation newCultivation = new PlayerCultivation(realm,target.getAttached(PLAYER_CULTIVATION).qi(),target.getAttached(PLAYER_CULTIVATION).meridianProgress());
        target.setAttached(PLAYER_CULTIVATION,newCultivation);
    }

    public static void setQi(AttachmentTarget target, int qi){
        PlayerCultivation newCultivation = new PlayerCultivation(target.getAttached(PLAYER_CULTIVATION).realm(),qi,target.getAttached(PLAYER_CULTIVATION).meridianProgress());
        target.setAttached(PLAYER_CULTIVATION,newCultivation);
    }

    public static void setMeridianProgress(AttachmentTarget target, Map<String, Integer> meridianProgress){
        PlayerCultivation newCultivation = new PlayerCultivation(target.getAttached(PLAYER_CULTIVATION).realm(),target.getAttached(PLAYER_CULTIVATION).qi(),meridianProgress);
        target.setAttached(PLAYER_CULTIVATION,newCultivation);
    }

    public static void setSpecificMeridianProgress(AttachmentTarget target, String key, int progress){
        Map<String, Integer> newProgress = target.getAttached(PLAYER_CULTIVATION).meridianProgress();
        newProgress.put(key,progress);
        PlayerCultivation newCultivation = new PlayerCultivation(target.getAttached(PLAYER_CULTIVATION).realm(),target.getAttached(PLAYER_CULTIVATION).qi(),newProgress);
        target.setAttached(PLAYER_CULTIVATION,newCultivation);
    }
}
