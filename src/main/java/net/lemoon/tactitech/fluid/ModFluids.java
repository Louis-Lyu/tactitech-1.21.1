package net.lemoon.tactitech.fluid;

import net.lemoon.tactitech.Tactitech;
import net.lemoon.tactitech.fluid.custom.IrradiatedAmmoniaFluid;
//import net.lemoon.tactitech.util.ModTags;
import com.google.common.collect.UnmodifiableIterator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.MapColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Iterator;

public class ModFluids {
    public static FlowableFluid IRRADIATED_AMMONIA;
    public static FlowableFluid FLOWING_IRRADIATED_AMMONIA;
    public static Block IRRADIATED_AMMONIA_BLOCK;

    public static Item IRRADIATED_AMMONIA_BUCKET;

//    public static boolean isGedritedWater(FluidState state) {
//        return state.isOf(ModFluids.GEDRITED_WATER) || state.isOf(ModFluids.FLOWING_GEDRITED_WATER);
//    }
//
//    public static boolean isTouchingGedritedWater(LivingEntity entity) {
//        return entity.updateMovementInFluid(ModTags.Fluids.GEDRITED_WATER, 0.014);
//    }


    public static void register() {
        IRRADIATED_AMMONIA = Registry.register(Registries.FLUID,
                Identifier.of(Tactitech.MOD_ID, "irradiated_ammonia_still"), new IrradiatedAmmoniaFluid.Still());

        FLOWING_IRRADIATED_AMMONIA = Registry.register(Registries.FLUID,
                Identifier.of(Tactitech.MOD_ID, "gedrited_water_flow"), new IrradiatedAmmoniaFluid.Flowing());

        IRRADIATED_AMMONIA_BLOCK = Registry.register(Registries.BLOCK,
                Identifier.of(Tactitech.MOD_ID, "gedrited_water_block"),
                new FluidBlock(ModFluids.IRRADIATED_AMMONIA, FabricBlockSettings
                        .copy(Blocks.WATER)
                        .mapColor(MapColor.DULL_PINK)) {});


       IRRADIATED_AMMONIA_BUCKET = Registry.register(Registries.ITEM,
                Identifier.of(Tactitech.MOD_ID, "gedrited_water_bucket"), new BucketItem(ModFluids.IRRADIATED_AMMONIA, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
    }

    static {
        Iterator var0 = Registries.FLUID.iterator();

        while(var0.hasNext()) {
            Fluid fluid = (Fluid)var0.next();
            UnmodifiableIterator var2 = fluid.getStateManager().getStates().iterator();

            while(var2.hasNext()) {
                FluidState fluidState = (FluidState)var2.next();
                Fluid.STATE_IDS.add(fluidState);
            }
        }

    }
}