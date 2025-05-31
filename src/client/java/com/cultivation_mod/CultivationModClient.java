package com.cultivation_mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class CultivationModClient implements ClientModInitializer {
	private static KeyBinding cultivationMenuKeybindings;

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		initKeybindings();
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