package com.cultivation_mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class CultivationModClient implements ClientModInitializer {
	private static KeyBinding cultivationMenuKeybindings;
	private static final BlockColorProvider CAULDRON_COLOR = (state, renderView, pos, index) ->
			renderView != null && pos != null ? BiomeColors.getWaterColor(renderView, pos) : -1;
	private static final BlockColorProvider SPIRIT_HERB_COLOR = (state, renderView, pos, index) ->
			renderView != null && pos != null ? state.getMapColor(renderView,pos).color : -1;


	@Override
	public void onInitializeClient() {
		initKeybindings();

		ColorProviderRegistry.BLOCK.register(CAULDRON_COLOR,CultivationModBlocks.JADE_CAULDRON);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), CultivationModBlocks.FIRE_SPIRIT_HERB_CROP);
		ColorProviderRegistry.BLOCK.register(SPIRIT_HERB_COLOR,CultivationModBlocks.FIRE_SPIRIT_HERB_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), CultivationModBlocks.WATER_SPIRIT_HERB_CROP);
		ColorProviderRegistry.BLOCK.register(SPIRIT_HERB_COLOR,CultivationModBlocks.WATER_SPIRIT_HERB_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), CultivationModBlocks.AIR_SPIRIT_HERB_CROP);
		ColorProviderRegistry.BLOCK.register(SPIRIT_HERB_COLOR,CultivationModBlocks.AIR_SPIRIT_HERB_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), CultivationModBlocks.EARTH_SPIRIT_HERB_CROP);
		ColorProviderRegistry.BLOCK.register(SPIRIT_HERB_COLOR,CultivationModBlocks.EARTH_SPIRIT_HERB_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), CultivationModBlocks.LIGHTNING_SPIRIT_HERB_CROP);
		ColorProviderRegistry.BLOCK.register(SPIRIT_HERB_COLOR,CultivationModBlocks.LIGHTNING_SPIRIT_HERB_CROP);

	}

	public void initKeybindings() {
		cultivationMenuKeybindings = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.cultivation_mod.cultivation_screen",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_C,
				"category.cultivation_mod.keybindings"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				while (cultivationMenuKeybindings.wasPressed()) {
						client.setScreen(new CultivationScreen(client.player));
				}
			}
		});
	}


}