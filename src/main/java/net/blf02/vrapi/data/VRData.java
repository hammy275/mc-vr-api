package net.blf02.vrapi.data;

import com.mojang.math.Matrix4f;
import net.blf02.vrapi.api.data.IVRData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * VRData Class.
 *
 * This class contains information about a given object tracked in VR (the HMD or a controller).
 */
public class VRData implements IVRData {

    protected final Vec3 position;
    protected final Vec3 lookVec;
    protected final float roll;
    protected final Matrix4f rotMatr;

    public VRData(Vec3 position, Vec3 lookVec, float roll, Matrix4f rotMatr) {
        this.position = position;
        this.lookVec = lookVec;
        this.roll = roll;
        this.rotMatr = rotMatr;
    }

    /**
     * Get the position of the object in Minecraft
     * @return A Vec3 representing the object's location in Minecraft
     */
    public Vec3 position() {
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
     * Gets the rotation matrix of the object.
     * Only on the client!
     * @return The Matrix4f representing the rotation of the object
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public Matrix4f getRotationMatrix() {
        if (this.rotMatr == null) {
            throw new IllegalArgumentException("You can only access the rotation matrix from the client side!");
        }
        return this.rotMatr;
    }

    /**
     * Get the direction of the object
     * @return A vector representing where the object is pointing on the x, y, and z axis.
     */
    public Vec3 getLookAngle() {
        return this.lookVec;
    }

    /**
     * Encode this VRData for network movement. Used internally
     * @param data Data to encode
     * @param buffer Buffer to encode into
     */
    public static void encode(VRData data, FriendlyByteBuf buffer) {
        buffer.writeDouble(data.position.x).writeDouble(data.position.y).writeDouble(data.position.z);
        buffer.writeDouble(data.lookVec.x).writeDouble(data.lookVec.y).writeDouble(data.lookVec.z);
        buffer.writeFloat(data.roll);
    }

    /**
     * Decode a buffer into VRData. Used internally
     * @param buffer Buffer to decode from
     * @return VRData as represented by the buffer
     */
    public static VRData decode(FriendlyByteBuf buffer) {
        Vec3 position = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        Vec3 lookVec = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        float roll = buffer.readFloat();
        return new VRData(position, lookVec, roll, null);
    }

}
