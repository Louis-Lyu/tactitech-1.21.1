package net.lemoon.tactitech.client.model.part;

import javax.annotation.Nullable;

import net.minecraft.util.math.Direction;

import net.lemoon.tactitech.pipe.PipeSpDef;

public class PipeSpSidedPartKey extends PipeSpPartKey {
    @Nullable
    public final Direction mainSide;

    public PipeSpSidedPartKey(PipeSpDef pipeDef, byte isConnected, Direction mainSide) {
        super(pipeDef, isConnected);
        this.mainSide = mainSide;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((mainSide == null) ? 0 : mainSide.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        PipeSpSidedPartKey other = (PipeSpSidedPartKey) obj;
        if (mainSide != other.mainSide) return false;
        return true;
    }
}
