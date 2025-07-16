package net.lemoon.tactitech.pipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.impl.EmptyFluidExtractable;
import alexiil.mc.lib.attributes.item.ItemExtractable;
import alexiil.mc.lib.attributes.item.impl.EmptyItemExtractable;

public class PipeSpBehaviourWood extends PipeSpBehaviourSided {

    private boolean lastRecv = false;
//    private boolean twodelay = true;
    private int extractionInterval = 10; // Extraction interval in ticks (0.5 seconds assuming 20 ticks per second)
    private int ticksSinceLastExtraction = 0;

    public PipeSpBehaviourWood(PartSpPipe pipe) {
        super(pipe);
    }

    @Override
    protected boolean canFaceDirection(Direction dir) {
        if (pipe.getNeighbourPipe(dir) != null) {
            return false;
        }
        return pipe.flow instanceof PipeSpFlowItem //
            ? pipe.getItemExtractable(dir) != EmptyItemExtractable.NULL //
            : pipe.getFluidExtractable(dir) != EmptyFluidExtractable.NULL;
    }

    @Override
    public void tick() {
        super.tick();
        World world = pipe.getPipeWorld();
        if (world.isClient) {
            return;
        }
        Direction dir = currentDirection();
        if (dir == null) {
            return;
        }

        ticksSinceLastExtraction++;

        if (ticksSinceLastExtraction >= extractionInterval) {
            ticksSinceLastExtraction = 0; // Reset the tick count
            if (!lastRecv) {
                lastRecv = true;
                tryExtract(dir); // Replace with actual direction parameter
            } else {
                lastRecv = false;

            }
        }
    }

    private void tryExtract(Direction dir) {
        if (pipe.flow instanceof PipeSpFlowItem) {
            tryExtractItems(dir, 1);
        } else {
            tryExtractFluids(dir);
        }
    }

    public void tryExtractItems(Direction dir, int count) {
        ItemExtractable extractable = pipe.getItemExtractable(dir);
        ItemStack stack = extractable.attemptAnyExtraction(1, Simulation.ACTION);

        if (!stack.isEmpty()) {
            ((PipeSpFlowItem) pipe.getFlow()).insertItemsForce(stack, dir, null, 0.08);
        }
    }

    public void tryExtractFluids(Direction dir) {
        ((PipeSpFlowFluid) pipe.getFlow()).tryExtract(dir);
    }
}
