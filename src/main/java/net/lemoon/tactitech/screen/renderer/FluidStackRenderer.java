package net.lemoon.tactitech.screen.renderer;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

// CREDIT: https://github.com/mezz/JustEnoughItems by mezz (Forge Version)
// HIGHLY EDITED VERSION FOR FABRIC by Kaupenjoe
// Under MIT-License: https://github.com/mezz/JustEnoughItems/blob/1.18/LICENSE.txt
public class FluidStackRenderer {
    private static final NumberFormat nf = NumberFormat.getIntegerInstance();
    public final long capacityMb;
    private final TooltipMode tooltipMode;
    private final int width;
    private final int height;

    enum TooltipMode {
        SHOW_AMOUNT,
        SHOW_AMOUNT_AND_CAPACITY,
        ITEM_LIST
    }

    public FluidStackRenderer() {
        this((FluidConstants.BUCKET / 81), TooltipMode.SHOW_AMOUNT_AND_CAPACITY, 16, 16);
    }

    public FluidStackRenderer(long capacityMb, boolean showCapacity, int width, int height) {
        this(capacityMb, showCapacity ? TooltipMode.SHOW_AMOUNT_AND_CAPACITY : TooltipMode.SHOW_AMOUNT, width, height);
    }

    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public FluidStackRenderer(int capacityMb, boolean showCapacity, int width, int height) {
        this(capacityMb, showCapacity ? TooltipMode.SHOW_AMOUNT_AND_CAPACITY : TooltipMode.SHOW_AMOUNT, width, height);
    }

    private FluidStackRenderer(long capacityMb, TooltipMode tooltipMode, int width, int height) {
        Preconditions.checkArgument(capacityMb > 0, "capacity must be > 0");
        Preconditions.checkArgument(width > 0, "width must be > 0");
        Preconditions.checkArgument(height > 0, "height must be > 0");
        this.capacityMb = capacityMb;
        this.tooltipMode = tooltipMode;
        this.width = width;
        this.height = height;
    }

    /*
    * METHOD FROM https://github.com/TechReborn/TechReborn
    * UNDER MIT LICENSE: https://github.com/TechReborn/TechReborn/blob/1.19/LICENSE.md
    */
    public void drawFluid(DrawContext context, SingleVariantStorage<FluidVariant> fluidStorage, int x, int y, int width, int height, long maxCapacity) {
        if (fluidStorage.variant.getFluid() == Fluids.EMPTY) {
            return;
        }
        RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
        y += height;
        final Sprite sprite = FluidVariantRendering.getSprite(fluidStorage.variant);
        int color = FluidVariantRendering.getColor(fluidStorage.variant);

        final int drawHeight = (int) Math.ceil((fluidStorage.amount / (maxCapacity * 1F) * height));
        final int iconHeight = sprite.getY();
        int offsetHeight = drawHeight;

        RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, 1F);

        int iteration = 0;
        while (offsetHeight != 0) {
            final int curHeight = Math.min(offsetHeight, iconHeight);

            context.drawSprite(x, y - offsetHeight, 0, width, curHeight, sprite);
            offsetHeight -= curHeight;
            iteration++;
            if (iteration > 50) {
                break;
            }
        }
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        RenderSystem.setShaderTexture(0, FluidRenderHandlerRegistry.INSTANCE.get(fluidStorage.variant.getFluid())
                .getFluidSprites(MinecraftClient.getInstance().world, null, fluidStorage.variant.getFluid().getDefaultState())[0].getAtlasId());
    }

    public List<Text> getTooltip(SingleVariantStorage<FluidVariant> fluidStorage, Item.TooltipContext tooltipFlag) {
        List<Text> tooltip = new ArrayList<>();
        FluidVariant fluidType = fluidStorage.variant;
        if (fluidType == null) {
            return tooltip;
        }

        MutableText displayName = Text.translatable("block." + Registries.FLUID.getId(fluidStorage.variant.getFluid()).toTranslationKey());
        tooltip.add(displayName);

        long amount = fluidStorage.amount;
        if (tooltipMode == TooltipMode.SHOW_AMOUNT_AND_CAPACITY) {
            MutableText amountString = Text.translatable("tactitech.tooltip.liquid.amount.with.capacity", nf.format(amount), nf.format(capacityMb));
            tooltip.add(amountString.fillStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)));
        } else if (tooltipMode == TooltipMode.SHOW_AMOUNT) {
            MutableText amountString = Text.translatable("tactitech.tooltip.liquid.amount", nf.format(amount));
            tooltip.add(amountString.fillStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)));
        }

        return tooltip;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}