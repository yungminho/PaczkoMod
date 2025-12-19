package org.paczkomod.paczkomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import org.paczkomod.paczkomod.PaczkoMod;

import java.util.concurrent.CompletableFuture;

public class PaczkoModEnglishLangGenerator extends FabricLanguageProvider {
    protected PaczkoModEnglishLangGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput,"en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        translationBuilder.add(PaczkoMod.PACZKOMOD_BLOCK, "Parcel Locker");

        translationBuilder.add(PaczkoMod.PACZKOMOD_ITEM, "Parcel Locker");

        translationBuilder.add("itemGroup.paczko-mod.functional_blocks", "Parcel Lockers");

        translationBuilder.add("container.paczko-mod.paczkomod", "Parcel Locker");
    }
}
