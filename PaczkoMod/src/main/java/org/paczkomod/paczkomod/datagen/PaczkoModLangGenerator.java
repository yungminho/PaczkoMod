package org.paczkomod.paczkomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import org.paczkomod.paczkomod.PaczkoMod;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class PaczkoModLangGenerator extends FabricLanguageProvider {

    public PaczkoModLangGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, "pl_pl", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder builder) {
        builder.add(PaczkoMod.PACZKOMOD_BLOCK, "Paczkomat");

        builder.add("itemGroup.paczko-mod.functional_blocks", "Paczkomaty");
    }
}