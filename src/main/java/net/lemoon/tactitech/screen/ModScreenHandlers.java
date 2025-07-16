package net.lemoon.tactitech.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.lemoon.tactitech.Tactitech;
import net.lemoon.tactitech.block.custom.ThermoGenerator;
import net.lemoon.tactitech.screen.custom.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.apache.http.impl.execchain.TunnelRefusedException;

public class ModScreenHandlers {
    public static final ScreenHandlerType<MolecularCompounderScreenHandler> MOLECULAR_COMPOUNDER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Tactitech.MOD_ID, "molecular_compounder_screen_handler"),
                    new ExtendedScreenHandlerType<>(MolecularCompounderScreenHandler::new, BlockPos.PACKET_CODEC));

    public static final ScreenHandlerType<ThermoGeneratorScreenHandler> THERMO_GENERATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Tactitech.MOD_ID, "thermo_generator_screen_handler"),
                    new ExtendedScreenHandlerType<>(ThermoGeneratorScreenHandler::new, BlockPos.PACKET_CODEC));


    public static final ScreenHandlerType<BasicAmmoniaExtractorScreenHandler> BASIC_AMMONIA_EXTRACTOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Tactitech.MOD_ID, "basic_ammonia_extractor_screen_handler"),
                    new ExtendedScreenHandlerType<>(BasicAmmoniaExtractorScreenHandler::new, BlockPos.PACKET_CODEC));

    public static final ScreenHandlerType<CoalGeneratorScreenHandler> COAL_GENERATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Tactitech.MOD_ID, "coal_generator_screen_handler"),
                    new ExtendedScreenHandlerType<>(CoalGeneratorScreenHandler::new, BlockPos.PACKET_CODEC));

    public static final ScreenHandlerType<TankScreenHandler> TANK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Tactitech.MOD_ID, "tank_screen_handler"),
                    new ExtendedScreenHandlerType<>(TankScreenHandler::new, BlockPos.PACKET_CODEC));


    public static void registerScreenHandlers() {
        Tactitech.LOGGER.info("Registering Screen Handlers for " + Tactitech.MOD_ID);
    }
}