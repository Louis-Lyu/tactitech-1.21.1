package net.lemoon.tactitech.pipe;

import alexiil.mc.lib.attributes.fluid.volume.ColouredFluidVolume;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;

public class PipeSpBehaviourSponge extends PipeSpBehaviour {

    public static final float RED = 0.625f;
    public static final float GREEN = 0.8125f;
    public static final float BLUE = 1f;
    public static final float ALPHA = 0.75f;

    private static final float RED_SQ = RED * RED;
    private static final float GREEN_SQ = GREEN * GREEN;
    private static final float BLUE_SQ = BLUE * BLUE;
    private static final float ALPHA_SQ = ALPHA * ALPHA;

    public PipeSpBehaviourSponge(PartSpPipe pipe) {
        super(pipe);
    }

    @Override
    public void tick() {
        super.tick();
        tickFluid((PipeSpFlowFluid) pipe.getFlow());
    }

    public static void tickFluid(PipeSpFlowFluid f) {
        FluidVolume fluid = f.centerSection.getFluid();
        if (fluid.isEmpty()) {
            return;
        }

        if (FluidKeys.WATER.equals(fluid.getFluidKey())) {
            ColouredFluidVolume vol = (ColouredFluidVolume) fluid;

            double r = vol.getRed();
            double g = vol.getGreen();
            double b = vol.getBlue();
            double a = vol.getAlpha();

            if (r == RED && g == GREEN && b == BLUE && a == ALPHA) {
                return;
            }

            double allowedChange = 1 / vol.amount().asInexactDouble() / 20;

            if (allowedChange < 0.002) {
                allowedChange = 0.002;
            }

            r *= r;
            g *= g;
            b *= b;
            a *= a;

            double dr = r - RED_SQ;
            double dg = g - GREEN_SQ;
            double db = b - BLUE_SQ;
            double da = a - ALPHA_SQ;

            double dt = Math.abs(dr) + Math.abs(dg) + Math.abs(db) + Math.abs(da);

            if (dt < allowedChange) {
                vol.setRgba(RED, GREEN, BLUE, ALPHA);
                return;
            }

            double part = allowedChange / dt;

            dr *= part;
            dg *= part;
            db *= part;
            da *= part;

            vol.setRgba(
                (float) Math.sqrt(r - dr), //
                (float) Math.sqrt(g - dg), //
                (float) Math.sqrt(b - db), //
                (float) Math.sqrt(a - da)
            );
        }
    }
}
