/*
 * Copyright © Wynntils 2023.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */
package com.wynntils.screens.container.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wynntils.core.components.Models;
import com.wynntils.screens.base.widgets.WynntilsButton;
import com.wynntils.utils.render.FontRenderer;
import com.wynntils.utils.render.RenderUtils;
import com.wynntils.utils.render.Texture;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class ContainerEditNameButton extends WynntilsButton {
    private static final List<Component> CANCEL_TOOLTIP = List.of(
            Component.translatable("screens.wynntils.containers.cancel.name").withStyle(ChatFormatting.RED),
            Component.translatable("screens.wynntils.containers.cancel.description")
                    .withStyle(ChatFormatting.GRAY));

    private static final List<Component> EDIT_TOOLTIP = List.of(
            Component.translatable("screens.wynntils.containers.edit.name").withStyle(ChatFormatting.YELLOW),
            Component.translatable("screens.wynntils.containers.edit.description")
                    .withStyle(ChatFormatting.GRAY));

    public ContainerEditNameButton(int x, int y, int width, int height) {
        super(x, y, width, height, Component.literal("Container Edit Button"));
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        RenderUtils.drawScalingTexturedRect(
                poseStack,
                Texture.EDIT_ICON.resource(),
                this.getX(),
                this.getY(),
                0,
                this.width,
                this.height,
                Texture.EDIT_ICON.width(),
                Texture.EDIT_ICON.height());

        if (isHovered) {
            List<Component> tooltipToUse = Models.Bank.isEditingName() ? CANCEL_TOOLTIP : EDIT_TOOLTIP;

            RenderUtils.drawTooltipAt(
                    poseStack,
                    mouseX,
                    mouseY,
                    100,
                    tooltipToUse,
                    FontRenderer.getInstance().getFont(),
                    true);
        }
    }

    // unused
    @Override
    public void onPress() {}

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY)) return false;

        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            Models.Bank.toggleEditingName(!Models.Bank.isEditingName());
        } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && !Models.Bank.isEditingName()) {
            Models.Bank.resetCurrentPageName();
        }

        return true;
    }
}
