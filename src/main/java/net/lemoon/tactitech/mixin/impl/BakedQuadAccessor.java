package net.lemoon.tactitech.mixin.impl;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;

@Mixin(BakedQuad.class)
public interface BakedQuadAccessor {
    @Accessor("sprite")
    Sprite simplepipes_getSprite();
}
