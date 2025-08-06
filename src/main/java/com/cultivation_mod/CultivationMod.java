package com.cultivation_mod;

import com.cultivation_mod.cultivation_setup.PlayerCultivation;
import com.cultivation_mod.cultivation_setup.PlayerCultivationAttachments;
import com.cultivation_mod.element_setup.PlayerElementAttachments;
import com.cultivation_mod.packet_payloads.CultScreenPayload;
import com.cultivation_mod.packet_payloads.TechniqueActionPayload;
import com.cultivation_mod.recipes.AlchemyRecipe;
import com.cultivation_mod.recipes.AlchemyRecipeSerializer;
import com.cultivation_mod.technique_setup.PlayerTechniqueAttachments;
import com.cultivation_mod.technique_setup.RegisteredTechnique;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class CultivationMod implements ModInitializer {
	public static final String MOD_ID = "cultivation_mod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		CultivationModComponents.initialize();
		CultivationModTags.initialize();
		CultivationModEffects.initialize();
		CultivationModItems.initialize();
		CultivationModBlocks.initialize();
		CultivationModBlockEntities.initialize();
		CultivationModEntities.initialize();
		PlayerCultivationAttachments.initialize();
		PlayerElementAttachments.initialize();
		RegisteredTechnique.initialize();

		Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(MOD_ID, AlchemyRecipeSerializer.ID), AlchemyRecipeSerializer.INSTANCE);
		Registry.register(Registries.RECIPE_TYPE, Identifier.of(MOD_ID, AlchemyRecipe.Type.ID), AlchemyRecipe.Type.INSTANCE);

		ServerEntityEvents.ENTITY_LOAD.register((entity,serverWorld) ->{
			if(entity instanceof PlayerEntity){
				if(PlayerElementAttachments.getCultivationElements(entity) == null){
					PlayerElementAttachments.setCultivationElements(entity,PlayerElementAttachments.createRandomInitElements((PlayerEntity) entity));
				}
				if(PlayerCultivationAttachments.getCultivation(entity) == null){
					PlayerCultivationAttachments.setCultivation(entity,new PlayerCultivation(-1,0, PlayerCultivationAttachments.initMeridians()));
				}
			}
		});

		PayloadTypeRegistry.playC2S().register(TechniqueActionPayload.ID, TechniqueActionPayload.TECH_ACTION_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(TechniqueActionPayload.ID, (payload, context) -> {
			if (payload.isPressed()) {
				if(Objects.equals(payload.keyPressed(), "main")){
					boolean doMarital = context.player().getStackInHand(context.player().getActiveHand()).isIn(ItemTags.WEAPON_ENCHANTABLE) && PlayerTechniqueAttachments.getTechnique(context.player(),"martial")!=null;
					PlayerTechniqueAttachments.runTechniqueEffect(context.player(),doMarital ? "martial":"spiritual","active");
				}
				else if(Objects.equals(payload.keyPressed(), "sensing")){
					PlayerTechniqueAttachments.runTechniqueEffect(context.player(),"sensing","active");
				}
				else if(Objects.equals(payload.keyPressed(), "special")){
					PlayerTechniqueAttachments.runTechniqueEffect(context.player(),"special","active");
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