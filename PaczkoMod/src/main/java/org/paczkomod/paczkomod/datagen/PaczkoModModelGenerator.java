package org.paczkomod.paczkomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.model.TextureSlot;
import org.paczkomod.paczkomod.PaczkoMod;
import org.paczkomod.paczkomod.block.PaczkoModBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;

public class PaczkoModModelGenerator extends FabricModelProvider {

    public PaczkoModModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {

        ResourceLocation singleModel = ModelTemplates.CUBE_ALL.create(
                PaczkoMod.PACZKOMOD_BLOCK,
                TextureMapping.cube(PaczkoMod.PACZKOMOD_BLOCK),
                generator.modelOutput
        );

        ResourceLocation casingBottom = ResourceLocation.fromNamespaceAndPath(PaczkoMod.MOD_ID, "block/paczkomod_casing_1");
        ResourceLocation casingTop = ResourceLocation.fromNamespaceAndPath(PaczkoMod.MOD_ID, "block/paczkomod_casing_2");

        ResourceLocation[] partModels = new ResourceLocation[8];

        for (int i = 0; i < 8; i++) {
            ResourceLocation frontTexture = ResourceLocation.fromNamespaceAndPath(PaczkoMod.MOD_ID, "block/paczkomod_" + i);
            ResourceLocation currentCasing = (i < 4) ? casingBottom : casingTop;
            ResourceLocation modelId = ResourceLocation.fromNamespaceAndPath(PaczkoMod.MOD_ID, "block/paczkomod_part_" + i);

            TextureMapping mapping = new TextureMapping()
                    .put(TextureSlot.FRONT, frontTexture)
                    .put(TextureSlot.SIDE, currentCasing)
                    .put(TextureSlot.TOP, currentCasing);

            partModels[i] = ModelTemplates.CUBE_ORIENTABLE.create(
                    modelId,
                    mapping,
                    generator.modelOutput
            );
        }

        MultiPartGenerator multiPart = MultiPartGenerator.multiPart(PaczkoMod.PACZKOMOD_BLOCK);

        for (Direction direction : Direction.Plane.HORIZONTAL) {

            VariantProperties.Rotation rotation = switch (direction) {
                case EAST -> VariantProperties.Rotation.R90;
                case SOUTH -> VariantProperties.Rotation.R180;
                case WEST -> VariantProperties.Rotation.R270;
                default -> VariantProperties.Rotation.R0;
            };

            multiPart.with(
                    Condition.condition()
                            .term(PaczkoModBlock.FACING, direction)
                            .term(PaczkoModBlock.FORMED, false),
                    Variant.variant()
                            .with(VariantProperties.MODEL, singleModel)
                            .with(VariantProperties.Y_ROT, rotation)
            );

            for (int i = 0; i < 8; i++) {
                multiPart.with(
                        Condition.condition()
                                .term(PaczkoModBlock.FACING, direction)
                                .term(PaczkoModBlock.FORMED, true)
                                .term(PaczkoModBlock.PART_ID, i),
                        Variant.variant()
                                .with(VariantProperties.MODEL, partModels[i])
                                .with(VariantProperties.Y_ROT, rotation)
                );
            }
        }

        generator.blockStateOutput.accept(multiPart);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }
}