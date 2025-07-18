/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

/*
 * Copyright (c) 2019 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */
package net.lemoon.tactitech.client.model;

import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import net.lemoon.tactitech.mixin.impl.BakedQuadAccessor;
import org.joml.Vector3f;

/** Holds all of the information necessary to make a {@link BakedQuad}. This provides a variety of methods to quickly
 * set or get different elements. This currently holds 4 {@link MutableVertex}. */
public class MutableQuad {
    public static final MutableQuad[] EMPTY_ARRAY = new MutableQuad[0];

    public final MutableVertex vertex_0 = new MutableVertex();
    public final MutableVertex vertex_1 = new MutableVertex();
    public final MutableVertex vertex_2 = new MutableVertex();
    public final MutableVertex vertex_3 = new MutableVertex();

    private int colourIndex = -1;
    private Direction face = null;
    private Sprite sprite = null;

    public MutableQuad() {}

    public MutableQuad(int tintIndex, Direction face) {
        this.colourIndex = tintIndex;
        this.face = face;
    }

    public MutableQuad(MutableQuad from) {
        copyFrom(from);
    }

    public MutableQuad copyFrom(MutableQuad from) {
        colourIndex = from.colourIndex;
        face = from.face;
        sprite = from.sprite;
        vertex_0.copyFrom(from.vertex_0);
        vertex_1.copyFrom(from.vertex_1);
        vertex_2.copyFrom(from.vertex_2);
        vertex_3.copyFrom(from.vertex_3);
        return this;
    }

    public MutableQuad setTint(int tint) {
        colourIndex = tint;
        return this;
    }

    public int getColourIndex() {
        return colourIndex;
    }

    public MutableQuad setFace(Direction face) {
        this.face = face;
        return this;
    }

    public Direction getFace() {
        return face;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public BakedQuad toBakedBlock() {
        // POS_TEX_COL_LIGHT_NORM
        int[] data = new int[32];
        vertex_0.toBakedBlock(data, 8 * 0);
        vertex_1.toBakedBlock(data, 8 * 1);
        vertex_2.toBakedBlock(data, 8 * 2);
        vertex_3.toBakedBlock(data, 8 * 3);
        return new BakedQuad(data, colourIndex, face, sprite, false);
    }

    public BakedQuad toBakedItem() {
        int[] data = new int[0];// TODO: Look into this!
        vertex_0.toBakedItem(data, 0);
        vertex_1.toBakedItem(data, 7);
        vertex_2.toBakedItem(data, 14);
        vertex_3.toBakedItem(data, 21);
        return new BakedQuad(data, colourIndex, face, sprite, false);
    }

    public MutableQuad fromBakedBlock(BakedQuad quad) {
        colourIndex = quad.getColorIndex();
        face = quad.getFace();
        sprite = ((BakedQuadAccessor) quad).simplepipes_getSprite();

        int[] data = quad.getVertexData();
        int stride = data.length / 4;

        vertex_0.fromBakedBlock(data, 0);
        vertex_1.fromBakedBlock(data, stride);
        vertex_2.fromBakedBlock(data, stride * 2);
        vertex_3.fromBakedBlock(data, stride * 3);

        return this;
    }

    public MutableQuad fromBakedItem(BakedQuad quad) {
        colourIndex = quad.getColorIndex();
        face = quad.getFace();
        sprite = ((BakedQuadAccessor) quad).simplepipes_getSprite();

        int[] data = quad.getVertexData();
        int stride = data.length / 4;

        vertex_0.fromBakedItem(data, 0);
        vertex_1.fromBakedItem(data, stride);
        vertex_2.fromBakedItem(data, stride * 2);
        vertex_3.fromBakedItem(data, stride * 3);

        return this;
    }

    public MutableQuad fromBakedFormat(BakedQuad quad, VertexFormat format) {
        colourIndex = quad.getColorIndex();
        face = quad.getFace();
        sprite = ((BakedQuadAccessor) quad).simplepipes_getSprite();

        int[] data = quad.getVertexData();
        int stride = data.length / 4;

        vertex_0.fromBakedFormat(data, format, 0);
        vertex_1.fromBakedFormat(data, format, stride);
        vertex_2.fromBakedFormat(data, format, stride * 2);
        vertex_3.fromBakedFormat(data, format, stride * 3);

        return this;
    }

    public void render(BufferBuilder bb) {
        vertex_0.render(bb);
        vertex_1.render(bb);
        vertex_2.render(bb);
        vertex_3.render(bb);
    }

    /** Outputs the vertex data into the given {@link QuadEmitter}. Doesn't call {@link QuadEmitter#emit()}. */
    public void putData(QuadEmitter emitter) {
        vertex_0.putData(0, emitter);
        vertex_1.putData(1, emitter);
        vertex_2.putData(2, emitter);
        vertex_3.putData(3, emitter);
    }

    public Vector3f getCalculatedNormal() {
        Vector3f a = vertex_1.positionvf();
        a.sub(vertex_0.positionvf());

        Vector3f b = vertex_2.positionvf();
        b.sub(vertex_0.positionvf());

        a.cross(b);
        return a;
    }

    public void setCalculatedNormal() {
        normalvf(getCalculatedNormal());
    }

    public static float diffuseLight(Vector3f normal) {
        return diffuseLight(normal.x, normal.y, normal.z);
    }

    public static float diffuseLight(float x, float y, float z) {
        boolean up = y >= 0;

        float xx = x * x;
        float yy = y * y;
        float zz = z * z;

        float t = xx + yy + zz;
        float light = (xx * 0.6f + zz * 0.8f) / t;

        float yyt = yy / t;
        if (!up) yyt *= 0.5;
        light += yyt;

        return light;
    }

    public float getCalculatedDiffuse() {
        return diffuseLight(getCalculatedNormal());
    }

    public void setDiffuse(Vector3f normal) {
        float diffuse = diffuseLight(normal);
        colourf(diffuse, diffuse, diffuse, 1);
    }

    public void setCalculatedDiffuse() {
        float diffuse = getCalculatedDiffuse();
        colourf(diffuse, diffuse, diffuse, 1);
    }

    /** Inverts a copy of this quad's normal so that it will render in the opposite direction. You will need to recall
     * diffusion calculations if you had previously calculated the diffuse. */
    public MutableQuad copyAndInvertNormal() {
        MutableQuad copy = new MutableQuad(this);
        copy.vertex_0.copyFrom(vertex_3).invertNormal();
        copy.vertex_1.copyFrom(vertex_2).invertNormal();
        copy.vertex_2.copyFrom(vertex_1).invertNormal();
        copy.vertex_3.copyFrom(vertex_0).invertNormal();
        return copy;
    }

    public MutableQuad rotateTextureUp(int times) {
        switch (times & 3) {
            case 0: {
                return this;
            }
            case 1: {
                Vec2f t = vertex_0.tex();
                vertex_0.texv(vertex_1.tex());
                vertex_1.texv(vertex_2.tex());
                vertex_2.texv(vertex_3.tex());
                vertex_3.texv(t);
                return this;
            }
            case 2: {
                Vec2f t0 = vertex_0.tex();
                Vec2f t1 = vertex_1.tex();
                vertex_0.texv(vertex_2.tex());
                vertex_1.texv(vertex_3.tex());
                vertex_2.texv(t0);
                vertex_3.texv(t1);
                return this;
            }
            case 3: {
                Vec2f t = vertex_3.tex();
                vertex_3.texv(vertex_2.tex());
                vertex_2.texv(vertex_1.tex());
                vertex_1.texv(vertex_0.tex());
                vertex_0.texv(t);
                return this;
            }
            default: {
                throw new IllegalStateException("'times & 3' was not 0, 1, 2 or 3!");
            }
        }
    }

    // ############################
    //
    // Delegate vertex functions
    //
    // Basically a lot of functions that
    // change every vertex in the same way
    //
    // ############################

    /* Position */

    // Note that you cannot set all of the position elements at once, so this is left empty

    /* Normal */

    /** Sets the normal for all vertices to the specified float coordinates. */
    public MutableQuad normalf(float x, float y, float z) {
        vertex_0.normalf(x, y, z);
        vertex_1.normalf(x, y, z);
        vertex_2.normalf(x, y, z);
        vertex_3.normalf(x, y, z);
        return this;
    }

    /** Sets the normal for all vertices to the specified double coordinates. */
    public MutableQuad normald(double x, double y, double z) {
        return normalf((float) x, (float) y, (float) z);
    }

    /** Sets the normal for all vertices to the specified {@link Vector3f}. */
    public MutableQuad normalvf(Vector3f vec) {
        return normalf(vec.x, vec.y, vec.z);
    }

    /** Sets the normal for all vertices to the specified {@link Vec3d}. */
    public MutableQuad normalvd(Vec3d vec) {
        return normald(vec.x, vec.y, vec.z);
    }

    // /** Sets the normal for all vertices to the specified {@link VecDouble}, using
    // * {@link VecDouble#a},{@link VecDouble#b}, and {@link VecDouble#c} */
    // public MutableQuad normalvd(VecDouble vec) {
    // return normald(vec.a, vec.b, vec.c);
    // }

    /** @return A new {@link Vector3f} with the normal of the first vertex. Only useful if the normal is expected to be
     *         the same for every vertex. */
    public Vector3f normalvf() {
        return new Vector3f(vertex_0.normal_x, vertex_0.normal_y, vertex_0.normal_z);
    }

    /** @return A new {@link Vec3d} with the normal of the first vertex. Only useful if the normal is expected to be the
     *         same for every vertex. */
    public Vec3d normalvd() {
        return new Vec3d(vertex_0.normal_x, vertex_0.normal_y, vertex_0.normal_z);
    }

    /* Colour */

    public MutableQuad colouri(int r, int g, int b, int a) {
        vertex_0.colouri(r, g, b, a);
        vertex_1.colouri(r, g, b, a);
        vertex_2.colouri(r, g, b, a);
        vertex_3.colouri(r, g, b, a);
        return this;
    }

    public MutableQuad colouri(int rgba) {
        vertex_0.colouri(rgba);
        vertex_1.colouri(rgba);
        vertex_2.colouri(rgba);
        vertex_3.colouri(rgba);
        return this;
    }

    public MutableQuad colourf(float r, float g, float b, float a) {
        vertex_0.colourf(r, g, b, a);
        vertex_1.colourf(r, g, b, a);
        vertex_2.colourf(r, g, b, a);
        vertex_3.colourf(r, g, b, a);
        return this;
    }

    // public MutableQuad colourvl(VecLong vec) {
    // return colouri((int) vec.a, (int) vec.b, (int) vec.c, (int) vec.d);
    // }
    //
    // public MutableQuad colourvf(Tuple4f vec) {
    // return colourf(vec.x, vec.y, vec.z, vec.w);
    // }

    public MutableQuad multColourd(double r, double g, double b, double a) {
        vertex_0.multColourd(r, g, b, a);
        vertex_1.multColourd(r, g, b, a);
        vertex_2.multColourd(r, g, b, a);
        vertex_3.multColourd(r, g, b, a);
        return this;
    }

    public MutableQuad multColourd(double by) {
        int m = (int) (by * 255);
        return multColouri(m);
    }

    public MutableQuad multColouri(int by) {
        vertex_0.multColouri(by);
        vertex_1.multColouri(by);
        vertex_2.multColouri(by);
        vertex_3.multColouri(by);
        return this;
    }

    public MutableQuad multColouri(int r, int g, int b, int a) {
        r &= 0xFF;
        g &= 0xFF;
        b &= 0xFF;
        a &= 0xFF;
        vertex_0.multColouri(r, g, b, a);
        vertex_1.multColouri(r, g, b, a);
        vertex_2.multColouri(r, g, b, a);
        vertex_3.multColouri(r, g, b, a);
        return this;
    }

    /** Multiplies every vertex by {@link #diffuseLight(float, float, float)} for the normal. */
    public MutableQuad multShade() {
        vertex_0.multShade();
        vertex_1.multShade();
        vertex_2.multShade();
        vertex_3.multShade();
        return this;
    }

    /* Texture co-ords */

    public MutableQuad texFromSprite(Sprite sprite) {
        vertex_0.texFromSprite(sprite);
        vertex_1.texFromSprite(sprite);
        vertex_2.texFromSprite(sprite);
        vertex_3.texFromSprite(sprite);
        return this;
    }

    /* Lightmap texture co-ords */

    public MutableQuad lighti(int block, int sky) {
        vertex_0.lighti(block, sky);
        vertex_1.lighti(block, sky);
        vertex_2.lighti(block, sky);
        vertex_3.lighti(block, sky);
        return this;
    }

    public MutableQuad lighti(int combined) {
        vertex_0.lighti(combined);
        vertex_1.lighti(combined);
        vertex_2.lighti(combined);
        vertex_3.lighti(combined);
        return this;
    }

    public MutableQuad lightf(float block, float sky) {
        return lighti((int) (block * 15), (int) (sky * 15));
    }

    public MutableQuad lightvf(Vec2f vec) {
        return lightf(vec.x, vec.y);
    }

    /** Sets the current light value of every vertex to be the maximum of the given in value, and the current value */
    public MutableQuad maxLighti(int block, int sky) {
        vertex_0.maxLighti(block, sky);
        vertex_1.maxLighti(block, sky);
        vertex_2.maxLighti(block, sky);
        vertex_3.maxLighti(block, sky);
        return this;
    }

    /* Transforms */

    // public MutableQuad transform(Matrix4f transformation) {
    // vertex_0.transform(transformation);
    // vertex_1.transform(transformation);
    // vertex_2.transform(transformation);
    // vertex_3.transform(transformation);
    // return this;
    // }

    public MutableQuad translatei(int x, int y, int z) {
        return translatef(x, y, z);
    }

    public MutableQuad translatef(float x, float y, float z) {
        vertex_0.translatef(x, y, z);
        vertex_1.translatef(x, y, z);
        vertex_2.translatef(x, y, z);
        vertex_3.translatef(x, y, z);
        return this;
    }

    public MutableQuad translated(double x, double y, double z) {
        return translatef((float) x, (float) y, (float) z);
    }

    public MutableQuad translatevi(Vec3i vec) {
        return translatei(vec.getX(), vec.getY(), vec.getZ());
    }

    public MutableQuad translatevf(Vector3f vec) {
        return translatef(vec.x, vec.y, vec.z);
    }

    public MutableQuad translatevd(Vec3d vec) {
        return translated(vec.x, vec.y, vec.z);
    }

    public MutableQuad scalef(float scale) {
        vertex_0.scalef(scale);
        vertex_1.scalef(scale);
        vertex_2.scalef(scale);
        vertex_3.scalef(scale);
        return this;
    }

    public MutableQuad scaled(double scale) {
        return scalef((float) scale);
    }

    public MutableQuad scalef(float x, float y, float z) {
        vertex_0.scalef(x, y, z);
        vertex_1.scalef(x, y, z);
        vertex_2.scalef(x, y, z);
        vertex_3.scalef(x, y, z);
        return this;
    }

    public MutableQuad scaled(double x, double y, double z) {
        return scalef((float) x, (float) y, (float) z);
    }

    public void rotateX(float angle) {
        vertex_0.rotateX(angle);
        vertex_1.rotateX(angle);
        vertex_2.rotateX(angle);
        vertex_3.rotateX(angle);
    }

    public void rotateY(float angle) {
        vertex_0.rotateY(angle);
        vertex_1.rotateY(angle);
        vertex_2.rotateY(angle);
        vertex_3.rotateY(angle);
    }

    public void rotateZ(float angle) {
        vertex_0.rotateZ(angle);
        vertex_1.rotateZ(angle);
        vertex_2.rotateZ(angle);
        vertex_3.rotateZ(angle);
    }

    public void rotateDirectlyX(float cos, float sin) {
        vertex_0.rotateDirectlyX(cos, sin);
        vertex_1.rotateDirectlyX(cos, sin);
        vertex_2.rotateDirectlyX(cos, sin);
        vertex_3.rotateDirectlyX(cos, sin);
    }

    public void rotateDirectlyY(float cos, float sin) {
        vertex_0.rotateDirectlyY(cos, sin);
        vertex_1.rotateDirectlyY(cos, sin);
        vertex_2.rotateDirectlyY(cos, sin);
        vertex_3.rotateDirectlyY(cos, sin);
    }

    public void rotateDirectlyZ(float cos, float sin) {
        vertex_0.rotateDirectlyZ(cos, sin);
        vertex_1.rotateDirectlyZ(cos, sin);
        vertex_2.rotateDirectlyZ(cos, sin);
        vertex_3.rotateDirectlyZ(cos, sin);
    }

    public MutableQuad rotate(Direction from, Direction to, float ox, float oy, float oz) {
        if (from == to) {
            // don't bother rotating: there is nothing to rotate!
            return this;
        }

        translatef(-ox, -oy, -oz);
        // @formatter:off
        switch (from.getAxis()) {
            case X: {
                int mult = from.getOffsetX();
                switch (to.getAxis()) {
                    case X: rotateY_180(); break;
                    case Y: rotateZ_90(mult * to.getOffsetY()); break;
                    case Z: rotateY_90(mult * to.getOffsetZ()); break;
                }
                break;
            }
            case Y: {
                int mult = from.getOffsetY();
                switch (to.getAxis()) {
                    case X: rotateZ_90(-mult * to.getOffsetX()); break;
                    case Y: rotateZ_180(); break;
                    case Z: rotateX_90(mult * to.getOffsetZ()); break;
                }
                break;
            }
            case Z: {
                int mult = -from.getOffsetZ();
                switch (to.getAxis()) {
                    case X: rotateY_90(mult * to.getOffsetX()); break;
                    case Y: rotateX_90(mult * to.getOffsetY()); break;
                    case Z: rotateY_180(); break;
                }
                break;
            }
        }
        // @formatter:on
        translatef(ox, oy, oz);
        return this;
    }

    public MutableQuad rotateX_90(float scale) {
        vertex_0.rotateX_90(scale);
        vertex_1.rotateX_90(scale);
        vertex_2.rotateX_90(scale);
        vertex_3.rotateX_90(scale);
        return this;
    }

    public MutableQuad rotateY_90(float scale) {
        vertex_0.rotateY_90(scale);
        vertex_1.rotateY_90(scale);
        vertex_2.rotateY_90(scale);
        vertex_3.rotateY_90(scale);
        return this;
    }

    public MutableQuad rotateZ_90(float scale) {
        vertex_0.rotateZ_90(scale);
        vertex_1.rotateZ_90(scale);
        vertex_2.rotateZ_90(scale);
        vertex_3.rotateZ_90(scale);
        return this;
    }

    public MutableQuad rotateX_180() {
        vertex_0.rotateX_180();
        vertex_1.rotateX_180();
        vertex_2.rotateX_180();
        vertex_3.rotateX_180();
        return this;
    }

    public MutableQuad rotateY_180() {
        vertex_0.rotateY_180();
        vertex_1.rotateY_180();
        vertex_2.rotateY_180();
        vertex_3.rotateY_180();
        return this;
    }

    public MutableQuad rotateZ_180() {
        vertex_0.rotateZ_180();
        vertex_1.rotateZ_180();
        vertex_2.rotateZ_180();
        vertex_3.rotateZ_180();
        return this;
    }

    @Override
    public String toString() {
        return "MutableQuad [vertices=" + vToS() + ", tintIndex=" + colourIndex + ", face=" + face + "]";
    }

    private String vToS() {
        return "[ " + vertex_0 + ", " + vertex_1 + ", " + vertex_2 + ", " + vertex_3 + " ]";
    }
}
