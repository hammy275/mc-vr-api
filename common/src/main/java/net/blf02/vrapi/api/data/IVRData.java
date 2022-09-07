package net.blf02.vrapi.api.data;

import com.mojang.math.Matrix4f;
import net.minecraft.world.phys.Vec3;

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
    public Vec3 getLookAngle();

    /**
     * Get the position of the object in Minecraft
     * @return A Vec3 representing the object's location in Minecraft
     */
    public Vec3 position();

    /**
     * Returns the roll of the object in degrees.
     * @return Object roll in degrees.
     */
    public float getRoll();

    /**
     * Returns the pitch of the object in degrees.
     * @return Object pitch in degrees.
     */
    public float getPitch();

    /**
     * Returns the yaw of the object in degrees.
     * @return Object yaw in degrees.
     */
    public float getYaw();

    /**
     * Gets the rotation matrix of the object.
     * THIS IS ONLY AVAILABLE CLIENT-SIDE!
     * @return The Matrix4f representing the rotation of the object
     */
    public Matrix4f getRotationMatrix();
}
