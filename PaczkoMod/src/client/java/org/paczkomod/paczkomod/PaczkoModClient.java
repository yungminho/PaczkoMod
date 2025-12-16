package org.paczkomod.paczkomod;

import org.paczkomod.paczkomod.client.PaczkoModScreen;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class PaczkoModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MenuScreens.register(PaczkoMod.PACZKOMOD_SCREEN_HANDLER, PaczkoModScreen::new);
    }
}
