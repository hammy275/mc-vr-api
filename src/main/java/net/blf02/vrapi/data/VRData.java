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

    public VRData(Vector3d position, Vector3d lookVec) {
        this.position = position;
        this.lookVec = lookVec;
    }

    /**
     * Get the position of the object in Minecraft
     * @return A Vector3d representing the object's location in Minecraft
     */
    public Vector3d getPosition() {
        return this.position;
    }

    /**
     * Get the direction of the object
     * @return A vector representing where the object is pointing on the x, y, and z axis.
     */
    public Vector3d getLookVec() {
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
    }

    /**
     * Decode a buffer into VRData. Used internally
     * @param buffer Buffer to decode from
     * @return VRData as represented by the buffer
     */
    public static VRData decode(PacketBuffer buffer) {
        Vector3d position = new Vector3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        Vector3d lookVec = new Vector3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        return new VRData(position, lookVec);
    }

}
