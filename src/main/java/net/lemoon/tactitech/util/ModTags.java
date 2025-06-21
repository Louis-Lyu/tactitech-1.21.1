package net.lemoon.tactitech.util;

import net.lemoon.tactitech.Tactitech;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> TACTITECH_BUILDING_BLOCKS = createTag("tactitech_building_blocks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Tactitech.MOD_ID, name));
        }
    }

    public static class Items {

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Tactitech.MOD_ID, name));
        }
    }

    public static class Fluids {

        private static TagKey<Fluid> createTag(String name) {
            return TagKey.of(RegistryKeys.FLUID, Identifier.of(Tactitech.MOD_ID, name));
        }
    }
}