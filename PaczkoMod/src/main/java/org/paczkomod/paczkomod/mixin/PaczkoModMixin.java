package org.paczkomod.paczkomod.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class PaczkoModMixin {
    @Inject(at = @At("HEAD"), method = "createLevels")
    private void init(CallbackInfo ci) {
        System.out.println("PaczkoMod Mixin (Main) loaded");
    }
}
