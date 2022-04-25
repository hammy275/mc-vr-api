package net.blf02.vrapi.data;

import net.blf02.vrapi.api.data.IVRData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;

/**
 * VRData Class.
 *
 * This class contains information about a given object tracked in VR (the HMD or a controller).
 */
public class VRData implements IVRData {

    protected final Vector3d position;
    protected final Vector3d lookVec;
    protected final float roll;

    public VRData(Vector3d position, Vector3d lookVec, float roll) {
        this.position = position;
        this.lookVec = lookVec;
        this.roll = roll;
    }

    /**
     * Get the position of the object in Minecraft
     * @return A Vector3d representing the object's location in Minecraft
     */
    public Vector3d position() {
        return this.position;
    }

    /**
     * Returns the roll of the controller in degrees.
     * @return Controller roll in degrees.
     */
    @Override
    public float getRoll() {
        return roll;
    }

    /**
     * Returns the pitch of the object in degrees.
     * @return Object pitch in degrees.
     */
    public float getPitch() {
        return (float) Math.toDegrees(Math.asin(this.lookVec.y / this.lookVec.length()));
    }

    /**
     * Returns the yaw of the object in degrees.
     * @return Object yaw in degrees.
     */
    public float getYaw() {
        return (float) Math.toDegrees(Math.atan2(-this.lookVec.x, this.lookVec.z));
    }

    /**
     * Get the direction of the object
     * @return A vector representing where the object is pointing on the x, y, and z axis.
     */
    public Vector3d getLookAngle() {
        return this.lookVec;
    }

    /**
     * Encode this VRData for network movement. Used internally
     * @param data Data to encode
     * @param buffer Buffer to encode into
     */
    public static void encode(VRData data, PacketBuffer buffer) {
        buffer.writeDouble(data.position.x).writeDouble(data.position.y).writeDouble(data.position.z);
        buffer.writeDouble(data.lookVec.x).writeDouble(data.lookVec.y).writeDouble(data.lookVec.z);
        buffer.writeFloat(data.roll);
    }

    /**
     * Decode a buffer into VRData. Used internally
     * @param buffer Buffer to decode from
     * @return VRData as represented by the buffer
     */
    public static VRData decode(PacketBuffer buffer) {
        Vector3d position = new Vector3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        Vector3d lookVec = new Vector3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        float roll = buffer.readFloat();
        return new VRData(position, lookVec, roll);
    }

}
