package net.lemoon.tactitech;

import alexiil.mc.lib.multipart.api.render.PartDynamicModelRegisterEvent;
import alexiil.mc.lib.multipart.api.render.PartStaticModelRegisterEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.lemoon.tactitech.block.custom.MolecularCompounder;
import net.lemoon.tactitech.client.model.SimplePipeModels;
import net.lemoon.tactitech.client.model.part.PipeSpPartBaker;
import net.lemoon.tactitech.client.model.part.PipeSpPartKey;
import net.lemoon.tactitech.client.render.GhostVertexConsumer;
import net.lemoon.tactitech.client.render.ItemPlacementGhostRenderer;
import net.lemoon.tactitech.client.render.PipePartRenderer;
import net.lemoon.tactitech.pipe.PartSpPipe;
import net.lemoon.tactitech.screen.custom.*;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.lemoon.tactitech.block.ModBlocks;
import net.lemoon.tactitech.block.entity.ModBlockEntities;
import net.lemoon.tactitech.screen.ModScreenHandlers;


public class TactitechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.MOLECULAR_COMPOUNDER_SCREEN_HANDLER, MolecularCompounderScreen::new);
        HandledScreens.register(ModScreenHandlers.THERMO_GENERATOR_SCREEN_HANDLER, ThermoGeneratorScreen::new);
        HandledScreens.register(ModScreenHandlers.BASIC_AMMONIA_EXTRACTOR_SCREEN_HANDLER, BasicAmmoniaExtractorScreen::new);
        HandledScreens.register(ModScreenHandlers.COAL_GENERATOR_SCREEN_HANDLER, CoalGeneratorScreen::new);
        HandledScreens.register(ModScreenHandlers.TANK_SCREEN_HANDLER, TankScreen ::new);

        ModelLoadingPlugin.register(SimplePipeModels::modelLoadingPlugin);


        PartStaticModelRegisterEvent.EVENT.register(model -> {
            model.register(PipeSpPartKey.class, new PipeSpPartBaker(model::getSprite));
        });
        PartDynamicModelRegisterEvent.EVENT.register(renderer -> {
            renderer.register(PartSpPipe.class, new PipePartRenderer());
        });
        //ClientTickEvents.END_CLIENT_TICK.register(client -> ItemPlacementGhostRenderer.clientTick());

//        WorldRenderEvents.START.register(GhostVertexConsumer::renderStart);
//        WorldRenderEvents.END.register(ItemPlacementGhostRenderer::render);
    }

}
