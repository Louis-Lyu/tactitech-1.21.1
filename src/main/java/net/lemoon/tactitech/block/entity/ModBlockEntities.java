package net.lemoon.tactitech.block.entity;

import net.lemoon.tactitech.Tactitech;
import net.lemoon.tactitech.block.ModBlocks;
import net.lemoon.tactitech.block.entity.custom.BasicAmmoniaExtractorEntity;
import net.lemoon.tactitech.block.entity.custom.CoalGeneratorBlockEntity;
import net.lemoon.tactitech.block.entity.custom.MolecularCompounderEntity;
import net.lemoon.tactitech.block.entity.custom.ThermoGeneratorEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities {
    public static final BlockEntityType<MolecularCompounderEntity> MOLECULAR_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Tactitech.MOD_ID, "molecular_be"),
                    BlockEntityType.Builder.create(MolecularCompounderEntity::new, ModBlocks.MOLECULAR_COMPOUNDER).build(null));

    public static final BlockEntityType<ThermoGeneratorEntity> THERMO_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Tactitech.MOD_ID, "thermo_be"),
                    BlockEntityType.Builder.create(ThermoGeneratorEntity::new, ModBlocks.THERMO_GENERATOR).build(null));

    public static final BlockEntityType<BasicAmmoniaExtractorEntity> EXTRACTOR_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Tactitech.MOD_ID, "extractor_be"),
                    BlockEntityType.Builder.create(BasicAmmoniaExtractorEntity::new, ModBlocks.BASIC_AMMONIA_EXTRACTOR).build(null));

    public static final BlockEntityType<CoalGeneratorBlockEntity> COAL_GENERATOR_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Tactitech.MOD_ID, "coal_generator_be"),
                    BlockEntityType.Builder.create(CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR).build(null));


    public static void registerBlockEntities() {
        Tactitech.LOGGER.info("Registering Block Entities for " + Tactitech.MOD_ID);

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, EXTRACTOR_BE);
    }
}