package org.paczkomod.paczkomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import org.paczkomod.paczkomod.PaczkoMod;

import java.util.concurrent.CompletableFuture;

public class PaczkoModRecipeGenerator extends FabricRecipeProvider {

    public PaczkoModRecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PaczkoMod.PACZKOMOD_BLOCK)
                .pattern("III")
                .pattern("IGI")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('G', Items.GOLD_INGOT)

                .unlockedBy("has_iron", has(Items.IRON_INGOT))

                .save(exporter);
    }
}