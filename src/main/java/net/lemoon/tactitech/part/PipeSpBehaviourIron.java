package net.lemoon.tactitech.part;

import net.minecraft.util.math.Direction;

import net.lemoon.tactitech.pipe.ISimplePipe;
import net.lemoon.tactitech.pipe.PartSpPipe;
import net.lemoon.tactitech.pipe.PipeSpFlowItem;
import net.lemoon.tactitech.pipe.PipeSpBehaviourSided;

public class PipeSpBehaviourIron extends PipeSpBehaviourSided {

    public PipeSpBehaviourIron(PartSpPipe pipe) {
        super(pipe);
    }

    @Override
    protected boolean canFaceDirection(Direction dir) {
        ISimplePipe neighbour = pipe.getNeighbourPipe(dir);
        if (neighbour != null) {
            return pipe.flow instanceof PipeSpFlowItem == neighbour.getFlow() instanceof PipeSpFlowItem;
        }
        return pipe.flow.hasInsertable(dir);
    }
}
