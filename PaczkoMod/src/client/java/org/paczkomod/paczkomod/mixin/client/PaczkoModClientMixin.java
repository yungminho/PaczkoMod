package org.paczkomod.paczkomod.mixin.client;

import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class PaczkoModClientMixin {
    @Inject(at = @At("HEAD"), method = "init")
    private void init(CallbackInfo info) {
        System.out.println("PaczkoMod Mixin (Client) loaded");
    }
}