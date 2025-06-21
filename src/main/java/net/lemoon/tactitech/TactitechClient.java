package net.lemoon.tactitech;

import net.fabricmc.api.ClientModInitializer;
import net.lemoon.tactitech.block.custom.MolecularCompounder;
import net.lemoon.tactitech.screen.custom.BasicAmmoniaExtractorScreen;
import net.lemoon.tactitech.screen.custom.CoalGeneratorScreen;
import net.lemoon.tactitech.screen.custom.ThermoGeneratorScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.lemoon.tactitech.block.ModBlocks;
import net.lemoon.tactitech.block.entity.ModBlockEntities;
import net.lemoon.tactitech.screen.ModScreenHandlers;
import net.lemoon.tactitech.screen.custom.MolecularCompounderScreen;


public class TactitechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.MOLECULAR_COMPOUNDER_SCREEN_HANDLER, MolecularCompounderScreen::new);
        HandledScreens.register(ModScreenHandlers.THERMO_GENERATOR_SCREEN_HANDLER, ThermoGeneratorScreen::new);
        HandledScreens.register(ModScreenHandlers.BASIC_AMMONIA_EXTRACTOR_SCREEN_HANDLER, BasicAmmoniaExtractorScreen::new);
        HandledScreens.register(ModScreenHandlers.COAL_GENERATOR_SCREEN_HANDLER, CoalGeneratorScreen::new);
    }

}
