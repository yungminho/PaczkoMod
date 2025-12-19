package org.paczkomod.paczkomod.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.paczkomod.paczkomod.screen.PaczkoModScreenHandler;

public class PaczkoModScreen extends AbstractContainerScreen<PaczkoModScreenHandler> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("paczko-mod", "textures/gui/paczkomod.png");

    public PaczkoModScreen(PaczkoModScreenHandler menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 222;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics gui, float tick, int mx, int my) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        gui.blit(
                RenderPipelines.GUI_TEXTURED,
                TEXTURE,
                x, y,
                0, 0,
                imageWidth, imageHeight,
                256, 256
        );
    }

    @Override
    public void render(GuiGraphics gui, int mx, int my, float tick) {
        this.renderBackground(gui, mx, my, tick);

        super.render(gui, mx, my, tick);

        renderTooltip(gui, mx, my);
    }
}