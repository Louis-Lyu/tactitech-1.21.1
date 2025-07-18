/*
 * Copyright (c) 2019 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */
package net.lemoon.tactitech.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.lemoon.tactitech.Tactitech;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

//import net.lemoon.tactitech.blocks.SimplePipeBlocks;
//import net.lemoon.tactitech.part.FacadeStateManager;
//import net.lemoon.tactitech.part.FullFacade;
import net.lemoon.tactitech.part.SimplePipeParts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimplePipeItems {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimplePipeItems.class);

    //public static final ItemFacade FACADE;

    public static final ItemPipePart WOODEN_PIPE_ITEMS;
    public static final ItemPipePart STONE_PIPE_ITEMS;
    public static final ItemPipePart CLAY_PIPE_ITEMS;
    public static final ItemPipePart IRON_PIPE_ITEMS;
    public static final ItemPipePart GOLD_PIPE_ITEMS;
    //public static final ItemPipePart DIAMOND_PIPE_ITEMS;

    public static final ItemPipePart WOODEN_PIPE_FLUIDS;

    public static final ItemPipePart STONE_PIPE_FLUIDS;
    public static final ItemPipePart CLAY_PIPE_FLUIDS;
    public static final ItemPipePart IRON_PIPE_FLUIDS;
    public static final ItemPipePart SPONGE_PIPE_FLUIDS;



//    public static final ItemSimplePart TANK;
//    public static final BlockItem PUMP;
//
//    public static final BlockItem TRIGGER_ITEM_INV_EMPTY;
//    public static final BlockItem TRIGGER_ITEM_INV_FULL;
//    public static final BlockItem TRIGGER_ITEM_INV_SPACE;
//    public static final BlockItem TRIGGER_ITEM_INV_CONTAINS;
//
//    public static final BlockItem TRIGGER_FLUID_INV_EMPTY;
//    public static final BlockItem TRIGGER_FLUID_INV_FULL;
//    public static final BlockItem TRIGGER_FLUID_INV_SPACE;
//    public static final BlockItem TRIGGER_FLUID_INV_CONTAINS;

    public static final ItemGroup MAIN_GROUP;

    static {
        Item.Settings pipes = new Item.Settings();



      //  FACADE = new ItemFacade(new Item.Settings().component(FullFacade.TYPE, FullFacade.DEFAULT));

        WOODEN_PIPE_ITEMS = new ItemPipePart(pipes, SimplePipeParts.WOODEN_PIPE_ITEMS);
        STONE_PIPE_ITEMS = new ItemPipePart(pipes, SimplePipeParts.STONE_PIPE_ITEMS);
        CLAY_PIPE_ITEMS = new ItemPipePart(pipes, SimplePipeParts.CLAY_PIPE_ITEMS);
        IRON_PIPE_ITEMS = new ItemPipePart(pipes, SimplePipeParts.IRON_PIPE_ITEMS);
        GOLD_PIPE_ITEMS = new ItemPipePart(pipes, SimplePipeParts.GOLD_PIPE_ITEMS);
        //DIAMOND_PIPE_ITEMS = new ItemPipePart(pipes, SimplePipeParts.DIAMOND_PIPE_ITEMS);

        WOODEN_PIPE_FLUIDS = new ItemPipePart(pipes, SimplePipeParts.WOODEN_PIPE_FLUIDS);
        STONE_PIPE_FLUIDS = new ItemPipePart(pipes, SimplePipeParts.STONE_PIPE_FLUIDS);
        CLAY_PIPE_FLUIDS = new ItemPipePart(pipes, SimplePipeParts.CLAY_PIPE_FLUIDS);
        LOGGER.debug("Rendering pipe with texture: pipe_clay_fluid.png");

        IRON_PIPE_FLUIDS = new ItemPipePart(pipes, SimplePipeParts.IRON_PIPE_FLUIDS);
        SPONGE_PIPE_FLUIDS = new ItemPipePart(pipes, SimplePipeParts.SPONGE_PIPE_FLUIDS);

        Item.Settings triggers = new Item.Settings();

//        TANK = new ItemSimplePart(triggers, SimplePipeParts.TANK, PartTank::new);
//        PUMP = new BlockItem(SimplePipeBlocks.PUMP, triggers);

//        TRIGGER_ITEM_INV_EMPTY = new BlockItem(SimplePipeBlocks.TRIGGER_ITEM_INV_EMPTY, triggers);
//        TRIGGER_ITEM_INV_FULL = new BlockItem(SimplePipeBlocks.TRIGGER_ITEM_INV_FULL, triggers);
//        TRIGGER_ITEM_INV_SPACE = new BlockItem(SimplePipeBlocks.TRIGGER_ITEM_INV_SPACE, triggers);
//        TRIGGER_ITEM_INV_CONTAINS = new BlockItem(SimplePipeBlocks.TRIGGER_ITEM_INV_CONTAINS, triggers);
//
//        TRIGGER_FLUID_INV_EMPTY = new BlockItem(SimplePipeBlocks.TRIGGER_FLUID_INV_EMPTY, triggers);
//        TRIGGER_FLUID_INV_FULL = new BlockItem(SimplePipeBlocks.TRIGGER_FLUID_INV_FULL, triggers);
//        TRIGGER_FLUID_INV_SPACE = new BlockItem(SimplePipeBlocks.TRIGGER_FLUID_INV_SPACE, triggers);
//        TRIGGER_FLUID_INV_CONTAINS = new BlockItem(SimplePipeBlocks.TRIGGER_FLUID_INV_CONTAINS, triggers);

        MAIN_GROUP = FabricItemGroup.builder().displayName(Text.translatable("itemgroups.tactitech.main"))
            .icon(SimplePipeItems::getMainGroupStack).entries((displayContext, entries) -> {
                entries.add(WOODEN_PIPE_ITEMS);
                entries.add(STONE_PIPE_ITEMS);
                entries.add(CLAY_PIPE_ITEMS);
                entries.add(IRON_PIPE_ITEMS);
                entries.add(GOLD_PIPE_ITEMS);
               // entries.add(DIAMOND_PIPE_ITEMS);

                entries.add(WOODEN_PIPE_FLUIDS);
                entries.add(STONE_PIPE_FLUIDS);
                entries.add(CLAY_PIPE_FLUIDS);
                entries.add(IRON_PIPE_FLUIDS);
                entries.add(SPONGE_PIPE_FLUIDS);

//                entries.add(TANK);
//                entries.add(PUMP);
//
//                entries.add(TRIGGER_ITEM_INV_EMPTY);
//                entries.add(TRIGGER_ITEM_INV_FULL);
//                entries.add(TRIGGER_ITEM_INV_SPACE);
//                entries.add(TRIGGER_ITEM_INV_CONTAINS);
//
//                entries.add(TRIGGER_FLUID_INV_EMPTY);
//                entries.add(TRIGGER_FLUID_INV_FULL);
//                entries.add(TRIGGER_FLUID_INV_SPACE);
//                entries.add(TRIGGER_FLUID_INV_CONTAINS);
            }).build();
    }

    private static ItemStack getMainGroupStack() {
        return new ItemStack(WOODEN_PIPE_ITEMS);
    }

//    private static ItemStack getFacadeGroupStack() {
//       // return FACADE.createItemStack(new FullFacade(FacadeStateManager.getPreviewState(), ItemFacade.DEFAULT_SHAPE));
//    }

    public static void load() {
        //registerItem(FACADE, "facade");

        registerItem(WOODEN_PIPE_ITEMS, "pipe_wooden_item");
        registerItem(STONE_PIPE_ITEMS, "pipe_stone_item");
        registerItem(CLAY_PIPE_ITEMS, "pipe_clay_item");
        registerItem(IRON_PIPE_ITEMS, "pipe_iron_item");
        registerItem(GOLD_PIPE_ITEMS, "pipe_gold_item");
        //registerItem(DIAMOND_PIPE_ITEMS, "pipe_diamond_item");

        registerItem(WOODEN_PIPE_FLUIDS, "pipe_wooden_fluid");
        registerItem(STONE_PIPE_FLUIDS, "pipe_stone_fluid");
        registerItem(CLAY_PIPE_FLUIDS, "pipe_clay_fluid");
        registerItem(IRON_PIPE_FLUIDS, "pipe_iron_fluid");
        registerItem(SPONGE_PIPE_FLUIDS, "pipe_sponge_fluid");

//        registerItem(TANK, "tank");
//        registerItem(PUMP, "pump");
//
//        registerItem(TRIGGER_ITEM_INV_EMPTY, "trigger_item_inv_empty");
//        registerItem(TRIGGER_ITEM_INV_FULL, "trigger_item_inv_full");
//        registerItem(TRIGGER_ITEM_INV_SPACE, "trigger_item_inv_space");
//        registerItem(TRIGGER_ITEM_INV_CONTAINS, "trigger_item_inv_contains");
//
//        registerItem(TRIGGER_FLUID_INV_EMPTY, "trigger_fluid_inv_empty");
//        registerItem(TRIGGER_FLUID_INV_FULL, "trigger_fluid_inv_full");
//        registerItem(TRIGGER_FLUID_INV_SPACE, "trigger_fluid_inv_space");
//        registerItem(TRIGGER_FLUID_INV_CONTAINS, "trigger_fluid_inv_contains");

        Registry.register(Registries.ITEM_GROUP, Tactitech.id("main"), MAIN_GROUP);
    }

    private static void registerItem(Item item, String name) {
        Registry.register(Registries.ITEM, Tactitech.MOD_ID + ":" + name, item);
    }
}
