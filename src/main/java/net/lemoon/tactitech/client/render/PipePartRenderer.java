package net.lemoon.tactitech.client.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

import net.lemoon.tactitech.pipe.PartSpPipe;
import net.lemoon.tactitech.pipe.PipeSpFlowFluid;
import net.lemoon.tactitech.pipe.PipeSpFlowItem;

import alexiil.mc.lib.multipart.api.render.PartRenderer;

public class PipePartRenderer implements PartRenderer<PartSpPipe> {

    @Override
    public void render(
        PartSpPipe part, float tickDelta, MatrixStack matrices, VertexConsumerProvider vc, int light, int overlay
    ) {
        if (part.flow instanceof PipeSpFlowItem) {
            PipeItemRenderer.render(tickDelta, matrices, vc, light, overlay, (PipeSpFlowItem) part.flow);
        } else if (part.flow instanceof PipeSpFlowFluid) {
            PipeFluidRenderer.render(matrices, vc, (PipeSpFlowFluid) part.flow, light);
        }
    }
}
