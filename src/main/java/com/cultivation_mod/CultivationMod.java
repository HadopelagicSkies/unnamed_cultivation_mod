package com.cultivation_mod;

import com.cultivation_mod.cultivation_setup.PlayerCultivation;
import com.cultivation_mod.cultivation_setup.PlayerCultivationAttatchments;
import com.cultivation_mod.element_setup.PlayerElementAttachments;
import com.cultivation_mod.recipes.AlchemyRecipe;
import com.cultivation_mod.recipes.AlchemyRecipeSerializer;
import com.cultivation_mod.status_effects.DantianSenseEffect;
import com.cultivation_mod.status_effects.QiEfficiencyEffect;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CultivationMod implements ModInitializer {
	public static final String MOD_ID = "cultivation_mod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		CultivationModItems.initialize();
		CultivationModBlocks.initialize();
		CultivationModBlockEntities.initialize();
		CultivationModComponents.initialize();
		CultivationModTags.initialize();
		CultivationModEffects.initialize();

		Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(MOD_ID, AlchemyRecipeSerializer.ID), AlchemyRecipeSerializer.INSTANCE);
		Registry.register(Registries.RECIPE_TYPE, Identifier.of(MOD_ID, AlchemyRecipe.Type.ID), AlchemyRecipe.Type.INSTANCE);

		ServerEntityEvents.ENTITY_LOAD.register((entity,serverWorld) ->{
			if(entity instanceof PlayerEntity){
				if(PlayerElementAttachments.getCultivationElements(entity) == null){
					PlayerElementAttachments.setCultivationElements(entity,PlayerElementAttachments.createRandomInitElements((PlayerEntity) entity));
				}
				if(PlayerCultivationAttatchments.getCultivation(entity) == null){
					PlayerCultivationAttatchments.setCultivation(entity,new PlayerCultivation(-1,0,PlayerCultivationAttatchments.initMeridians()));
				}
			}
		});

		PayloadTypeRegistry.playC2S().register(CultScreenPayload.ID, CultScreenPayload.CULT_SCREEN_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(CultScreenPayload.ID, (payload, context) -> {
			ProcessCultivation.processCultivation(context.player(), payload.pattern());
		});

		LOGGER.info("cultivation mod Loaded");
	}
}