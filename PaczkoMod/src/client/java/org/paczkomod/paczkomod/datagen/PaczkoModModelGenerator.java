package org.paczkomod.paczkomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedList;
import org.paczkomod.paczkomod.PaczkoMod;
import org.paczkomod.paczkomod.block.PaczkoModBlock;
import com.mojang.math.Quadrant;

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
                    .put(TextureSlot.PARTICLE, currentCasing)
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

            Quadrant rotation = switch (direction) {
                case EAST -> Quadrant.R90;
                case SOUTH -> Quadrant.R180;
                case WEST -> Quadrant.R270;
                default -> Quadrant.R0;
            };

            ConditionBuilder unformedCondition = new ConditionBuilder()
                    .term(PaczkoModBlock.FACING, direction)
                    .term(PaczkoModBlock.FORMED, false);

            Variant unformedVariant = new Variant(singleModel)
                    .withYRot(rotation);

            multiPart.with(unformedCondition, new MultiVariant(WeightedList.of(unformedVariant)));


            for (int i = 0; i < 8; i++) {
                ConditionBuilder formedCondition = new ConditionBuilder()
                        .term(PaczkoModBlock.FACING, direction)
                        .term(PaczkoModBlock.FORMED, true)
                        .term(PaczkoModBlock.PART_ID, i);

                Variant formedVariant = new Variant(partModels[i])
                        .withYRot(rotation);

                multiPart.with(formedCondition, new MultiVariant(WeightedList.of(formedVariant)));
            }
        }

        generator.blockStateOutput.accept(multiPart);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }
}