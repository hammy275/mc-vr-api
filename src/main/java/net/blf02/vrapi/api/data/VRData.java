package net.blf02.vrapi.api.data;

import net.minecraft.util.math.vector.Vector3d;

public class VRData {

    protected final Vector3d position;
    protected final Vector3d lookVec;

    public VRData(Vector3d position, Vector3d lookVec) {
        this.position = position;
        this.lookVec = lookVec;
    }

    public Vector3d getPosition() {
        return this.position;
    }

    public Vector3d getLookVec() {
        return this.lookVec;
    }

}
