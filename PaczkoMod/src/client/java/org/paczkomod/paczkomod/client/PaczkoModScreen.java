package org.paczkomod.paczkomod.client;

import net.minecraft.client.gui.GuiGraphics;
import org.paczkomod.paczkomod.screen.PaczkoModScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PaczkoModScreen extends AbstractContainerScreen<PaczkoModScreenHandler> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("paczko-mod", "textures/gui/paczkomod.png");

    public PaczkoModScreen(PaczkoModScreenHandler menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 222;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics gui, float tick, int mx, int my) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(TEXTURE, (width - imageWidth) / 2, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics gui, int mx, int my, float tick) {
        super.render(gui, mx, my, tick);
        renderTooltip(gui, mx, my);
    }
}