package org.paczkomod.paczkomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.paczkomod.paczkomod.PaczkoMod;

import java.util.concurrent.CompletableFuture;

public class PaczkoModRecipeGenerator extends FabricRecipeProvider {

    public PaczkoModRecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderGetter<Item> items = registryLookup.lookupOrThrow(Registries.ITEM);

                ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, PaczkoMod.PACZKOMOD_BLOCK)
                        .pattern("III")
                        .pattern("IGI")
                        .pattern("III")
                        .define('I', Items.IRON_INGOT)
                        .define('G', Items.GOLD_INGOT)
                        .unlockedBy("has_iron", this.has(Items.IRON_INGOT))
                        .save(exporter);
            }
        };
    }

    @Override
    public @NotNull String getName() {
        return "PaczkoMod Recipes Generator";
    }
}