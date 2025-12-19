package org.paczkomod.paczkomod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.paczkomod.paczkomod.screen.PaczkoModScreenHandler;
import org.paczkomod.paczkomod.block.PaczkoModBlock;

public class PaczkoMod implements ModInitializer {
    public static final String MOD_ID = "paczko-mod";

    public static final ResourceKey<Block> PACZKOMOD_BLOCK_KEY = ResourceKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "paczkomod")
    );

    public static final ResourceKey<Item> PACZKOMOD_ITEM_KEY = ResourceKey.create(
            Registries.ITEM,
            PACZKOMOD_BLOCK_KEY.location()
    );

    public static final PaczkoModBlock PACZKOMOD_BLOCK = new PaczkoModBlock(
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .setId(PACZKOMOD_BLOCK_KEY)
    );

    public static final BlockItem PACZKOMOD_ITEM = new BlockItem(
            PACZKOMOD_BLOCK,
            new Item.Properties().setId(PACZKOMOD_ITEM_KEY)
    );

    public static MenuType<PaczkoModScreenHandler> PACZKOMOD_SCREEN_HANDLER;

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.BLOCK, PACZKOMOD_BLOCK_KEY.location(), PACZKOMOD_BLOCK);

        Registry.register(BuiltInRegistries.ITEM, PACZKOMOD_ITEM_KEY.location(), PACZKOMOD_ITEM);

        PACZKOMOD_SCREEN_HANDLER = Registry.register(
                BuiltInRegistries.MENU,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "paczkomod_screen"),
                new MenuType<>(PaczkoModScreenHandler::new, FeatureFlagSet.of())
        );
    }
}