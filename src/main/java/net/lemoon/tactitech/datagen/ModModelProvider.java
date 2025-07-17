package net.lemoon.tactitech.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.lemoon.tactitech.block.ModBlocks;
import net.lemoon.tactitech.item.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MOLECULAR_COMPOUNDER);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOLIDIFIED_AMMONIA);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.COAL_GENERATOR);
        blockStateModelGenerator.registerCooker(ModBlocks.BASIC_AMMONIA_EXTRACTOR, TexturedModel.ORIENTABLE);
//        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PINK_GARNET_BLOCK);
//        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_PINK_GARNET_BLOCK);
//        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PINK_GARNET_ORE);
//        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PINK_GARNET_DEEPSLATE_ORE);
//
//        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MAGIC_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(ModItems.AMMONIA_POWDER, Models.GENERATED);
//        itemModelGenerator.register(ModItems.RAW_PINK_GARNET, Models.GENERATED);
//
//        itemModelGenerator.register(ModItems.CAULIFLOWER, Models.GENERATED);
//        itemModelGenerator.register(ModItems.CHISEL, Models.GENERATED);
//        itemModelGenerator.register(ModItems.STARLIGHT_ASHES, Models.GENERATED);
    }
}