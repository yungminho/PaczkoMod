package org.paczkomod.paczkomod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.paczkomod.paczkomod.screen.PaczkoModScreenHandler;
import org.paczkomod.paczkomod.block.PaczkoModBlock;

public class PaczkoMod implements ModInitializer {
    public static final String MOD_ID = "paczko-mod";

    public static final PaczkoModBlock PACZKOMOD_BLOCK =  new PaczkoModBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static MenuType<PaczkoModScreenHandler> PACZKOMOD_SCREEN_HANDLER;

    @Override
    public void onInitialize() {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MOD_ID, "paczkomod");

        Registry.register(BuiltInRegistries.BLOCK, id, PACZKOMOD_BLOCK);
        Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(PACZKOMOD_BLOCK, new Item.Properties()));

        PACZKOMOD_SCREEN_HANDLER = Registry.register(
                BuiltInRegistries.MENU,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "paczkomod_screen"),
                new MenuType<>(PaczkoModScreenHandler::new, FeatureFlagSet.of())
        );
    }
}
