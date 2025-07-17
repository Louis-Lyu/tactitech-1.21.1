package net.lemoon.tactitech.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.lemoon.tactitech.Tactitech;
import net.lemoon.tactitech.fluid.ModFluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item GAS_CANISTERS = registerItem("gas_canisters", new Item(new Item.Settings()));
    public static final Item AMMONIA_POWDER = registerItem("ammonia_powder", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Tactitech.MOD_ID, name), item);
    }

    public static void registerModItems () {
        Tactitech.LOGGER.info("Registering Mod Items For" + Tactitech.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(GAS_CANISTERS);
            entries.add(ModFluids.IRRADIATED_AMMONIA_BUCKET);
            entries.add(AMMONIA_POWDER);
        });
    }
}
