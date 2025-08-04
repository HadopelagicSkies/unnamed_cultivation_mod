package com.cultivation_mod.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CultivationModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(UnnamedCultivationModModelProvider::new);
		pack.addProvider(UnnamedCultivationModBlockTagProvider::new);
		pack.addProvider(UnnamedCultivationModBlockLootTableProvider::new);
	}
}
