package net.lemoon.tactitech.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public record BasicAmmoniaExtractorRecipe(Ingredient inputItem, ItemStack output) implements Recipe<BasicAmmoniaExtractorRecipeInput> {
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public boolean matches(BasicAmmoniaExtractorRecipeInput input, World world) {
        if(world.isClient()) {
            return false;
        }

        return inputItem.test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(BasicAmmoniaExtractorRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BASIC_AMMONIA_EXTRACTOR_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.EXTRACTOR_TYPE;
    }

    public static class Serializer implements RecipeSerializer<BasicAmmoniaExtractorRecipe> {
        public static final MapCodec<BasicAmmoniaExtractorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(BasicAmmoniaExtractorRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(BasicAmmoniaExtractorRecipe::output)
        ).apply(inst, BasicAmmoniaExtractorRecipe::new));
        public static final PacketCodec<RegistryByteBuf, BasicAmmoniaExtractorRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, BasicAmmoniaExtractorRecipe::inputItem,
                        ItemStack.PACKET_CODEC, BasicAmmoniaExtractorRecipe::output,
                        BasicAmmoniaExtractorRecipe::new);

        @Override
        public MapCodec<BasicAmmoniaExtractorRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, BasicAmmoniaExtractorRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }
}
