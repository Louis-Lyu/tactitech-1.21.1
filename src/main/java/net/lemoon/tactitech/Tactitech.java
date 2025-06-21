package net.lemoon.tactitech;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.lemoon.tactitech.block.ModBlocks;
import net.lemoon.tactitech.block.entity.ModBlockEntities;
import net.lemoon.tactitech.block.entity.custom.BasicAmmoniaExtractorEntity;
import net.lemoon.tactitech.fluid.ModFluids;
import net.lemoon.tactitech.item.ModItemGroups;
import net.lemoon.tactitech.item.ModItems;
import net.lemoon.tactitech.recipe.ModRecipes;
import net.lemoon.tactitech.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Tactitech implements ModInitializer {
	public static final String MOD_ID = "tactitech";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
			ModItemGroups.registerItemGroups();
			ModFluids.register();
			ModItems.registerModItems();
			ModBlocks.registerModBlocks();
			ModBlockEntities.registerBlockEntities();
			ModScreenHandlers.registerScreenHandlers();
			ModRecipes.registerRecipes();



//			BlockEntityType<BasicAmmoniaExtractorEntity> MACHINE = Optional.empty();;
//
//		FluidStorage.SIDED.registerForBlockEntity((machine, direction) -> switch (direction) {
//					// Only expose the water tank from the top side.
//					case UP -> machine.exposedWaterTank;
//					// Only expose the lava tank from the bottom side.
//					case DOWN -> machine.exposedLavaTank;
//					// Expose both otherwise (access from one of the 4 sides).
//					default -> machine.exposedTanks;
//			}, MACHINE);

		}
	}
