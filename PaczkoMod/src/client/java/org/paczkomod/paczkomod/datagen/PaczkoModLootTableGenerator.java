package org.paczkomod.paczkomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import org.paczkomod.paczkomod.PaczkoMod;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class PaczkoModLootTableGenerator extends FabricBlockLootTableProvider {

    public PaczkoModLootTableGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(PaczkoMod.PACZKOMOD_BLOCK);
    }
}