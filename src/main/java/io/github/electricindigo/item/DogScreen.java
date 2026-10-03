package io.github.electricindigo.item;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class DogScreen extends AbstractContainerScreen<DogMenu>
{
    private static final Identifier BACKGROUND = Identifier.withDefaultNamespace("textures/gui/container/horse.png");
    private static final Identifier SLOT = Identifier.withDefaultNamespace("container/slot");

    public DogScreen(DogMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT, leftPos + 7, topPos + 17, 18, 18);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT, leftPos + 7, topPos + 35, 18, 18);

        if (menu.getWolf() != null)
        {
            InventoryScreen.renderEntityInInventoryFollowsAngle(graphics, leftPos + 26, topPos + 18, leftPos + 78, topPos + 70,
                    17, 0.25F, 0, 0, menu.getWolf());
        }
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY)
    {
        super.extractTooltip(graphics, mouseX, mouseY);
    }
}
