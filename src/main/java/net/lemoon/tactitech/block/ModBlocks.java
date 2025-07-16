package net.lemoon.tactitech.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.lemoon.tactitech.Tactitech;
import net.lemoon.tactitech.block.custom.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


public class ModBlocks {
    public static final Block SOLIDIFIED_AMMONIA = registerBlock("solidified_ammonia",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f,3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)
            ));
    public static final Block MOLECULAR_COMPOUNDER = registerBlock("molecular_compounder",
            new MolecularCompounder(AbstractBlock.Settings.create()
                    .strength(4f,5f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.METAL)
                    .nonOpaque()
            ));
    public static final Block THERMO_GENERATOR = registerBlock("thermo_generator",
            new ThermoGenerator(AbstractBlock.Settings.create()
                    .strength(4f,5f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.METAL)
                    .nonOpaque()
            ));

    public static final Block BASIC_AMMONIA_EXTRACTOR = registerBlock("basic_ammonia_extractor",
            new BasicAmmoniaExtractor(AbstractBlock.Settings.create()
                    .strength(4f,5f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.METAL)
                    .nonOpaque()
            ));
    public static final Block COAL_GENERATOR = registerBlock("coal_generator",
            new CoalGeneratorBlock(AbstractBlock.Settings.create()
                    .strength(4f,5f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.METAL)
                    .nonOpaque()
            ));
    public static final Block TANK_BLOCK = registerBlock("tank_block",
            new TankBlock(AbstractBlock.Settings.create()
                    .strength(4f,5f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.METAL)
                    .nonOpaque()
            ));


    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Tactitech.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Tactitech.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks () {
        Tactitech.LOGGER.info("Registering Mod Blocks For" + Tactitech.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.SOLIDIFIED_AMMONIA);
            entries.add(ModBlocks.MOLECULAR_COMPOUNDER);
            entries.add(ModBlocks.THERMO_GENERATOR);
            entries.add(ModBlocks.BASIC_AMMONIA_EXTRACTOR);
            entries.add(ModBlocks.COAL_GENERATOR);
            entries.add(ModBlocks.TANK_BLOCK);
        });
    }
}
