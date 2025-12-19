package org.paczkomod.paczkomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PaczkoModModDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(PaczkoModModelGenerator::new);
        pack.addProvider(PaczkoModRecipeGenerator::new);
        pack.addProvider(PaczkoModLootTableGenerator::new);
        pack.addProvider(PaczkoModLangGenerator::new);
        pack.addProvider(PaczkoModEnglishLangGenerator::new);
    }
}
