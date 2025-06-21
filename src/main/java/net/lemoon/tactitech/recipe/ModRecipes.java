package net.lemoon.tactitech.recipe;

import net.lemoon.tactitech.Tactitech;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeSerializer<BasicAmmoniaExtractorRecipe> BASIC_AMMONIA_EXTRACTOR_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(Tactitech.MOD_ID, "extracting"), new BasicAmmoniaExtractorRecipe.Serializer());
    public static final RecipeType<BasicAmmoniaExtractorRecipe> EXTRACTOR_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(Tactitech.MOD_ID, "extracting"), new RecipeType<>() {
                @Override
                public String toString() {
                    return "extracting";
                }
            });

    public static void registerRecipes() {
        Tactitech.LOGGER.info("Registering Custom Recipes for " + Tactitech.MOD_ID);
    }
}