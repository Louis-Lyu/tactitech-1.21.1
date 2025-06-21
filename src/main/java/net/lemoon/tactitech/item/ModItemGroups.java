package net.lemoon.tactitech.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.lemoon.tactitech.Tactitech;
import net.lemoon.tactitech.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup TACTITECH_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Tactitech.MOD_ID, "tactitech_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.GAS_CANISTERS))
                    .displayName(Text.translatable("itemgroup.tactitech.tactitech_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.GAS_CANISTERS);
                        //entries.add(ModItems.RAW_PINK_GARNET);
                    }).build());

    public static final ItemGroup TACTITECH_BLOCKS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Tactitech.MOD_ID, "tactitech_blocks"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.SOLIDIFIED_AMMONIA))
                    .displayName(Text.translatable("itemgroup.tactitech.tactitech_blocks"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.SOLIDIFIED_AMMONIA);
                        entries.add(ModBlocks.MOLECULAR_COMPOUNDER);
                        entries.add(ModBlocks.THERMO_GENERATOR);
                    }).build());


    public static void registerItemGroups () {

        Tactitech.LOGGER.info("Registering Item Groups For" + Tactitech.MOD_ID);
    }
}
