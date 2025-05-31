package com.cultivation_mod;

import com.cultivation_mod.element_setup.PlayerElementAttachments;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CultivationMod implements ModInitializer {
	public static final String MOD_ID = "cultivation-mod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ServerEntityEvents.ENTITY_LOAD.register((entity,serverWorld) ->{
			if(entity instanceof PlayerEntity){
				if(PlayerElementAttachments.getCultivationElements(entity) == null){
					PlayerElementAttachments.setCultivationElements(entity,PlayerElementAttachments.createRandomInitElements((PlayerEntity) entity));
				}
			}
		});

		LOGGER.info("cultivation mod Loaded");
	}
}