package net.blf02.vrapi.api.data;

import net.minecraft.util.math.vector.Vector3d;

/**
 * IVRData Interface.
 *
 * This interface contains information about a given object tracked in VR (the HMD or a controller).
 */
public interface IVRData {

    /**
     * Get the direction of the object
     * @return A vector representing where the object is pointing on the x, y, and z axis.
     */
    public Vector3d getLookAngle();

    /**
     * Get the position of the object in Minecraft
     * @return A Vector3d representing the object's location in Minecraft
     */
    public Vector3d position();
}
