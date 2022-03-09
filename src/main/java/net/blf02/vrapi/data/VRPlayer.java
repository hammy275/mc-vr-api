package net.blf02.vrapi.data;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.minecraft.network.PacketBuffer;

/**
 * VRPlayer contains all the information related to a player in VR.
 */
public class VRPlayer implements IVRPlayer {

    protected final VRData hmd;
    protected final VRData controller0;
    protected final VRData controller1;
    protected final VRData leftEye;
    protected final VRData rightEye;

    public VRPlayer(VRData hmd, VRData controller0, VRData controller1,
                    VRData leftEye, VRData rightEye) {
        this.hmd = hmd;
        this.controller0 = controller0;
        this.controller1 = controller1;
        this.leftEye = leftEye;
        this.rightEye = rightEye;
    }

    /**
     * Get HMD information
     * @return VRData representing the position and rotation of the HMD
     */
    public VRData getHMD() {
        return this.hmd;
    }

    /**
     * Get Controller 0 information
     * @return VRData representing the position and rotation of Controller 0 (usually the right hand)
     */
    public VRData getController0() {
        return this.controller0;
    }

    /**
     * Get Controller 1 information
     * @return VRData representing the position and rotation of Controller 0 (usually the left hand)
     */
    public VRData getController1() {
        return this.controller1;
    }

    /**
     * Get Controller information
     * @param controller The controller number to retrieve (0 or 1).
     * @return VRData representing the requested controller
     */
    public VRData getController(int controller) {
        if (controller != 0 && controller != 1) {
            throw new IllegalArgumentException("Controller " + controller + " invalid! You can only select controllers 0 or 1.");
        }
        return controller == 0 ? getController0() : getController1();
    }

    /**
     * Get Left Eye information
     * @return VRData representing the position and look direction of the left eye
     */
    @Override
    public VRData getLeftEye() {
        return this.leftEye;
    }

    /**
     * Get Right Eye information
     * @return VRData representing the position and look direction of the right eye
     */
    @Override
    public VRData getRightEye() {
        return this.rightEye;
    }

    /**
     * Get information for an eye.
     * @param eye Eye number to get. 0 for left, 1 for right.
     * @return VRData representing the eye.
     */
    @Override
    public VRData getEye(int eye) {
        if (eye == 0) {
            return this.getLeftEye();
        } else if (eye == 1) {
            return this.getRightEye();
        } else {
            throw new IllegalArgumentException("Eye " + eye + " invalid! You can only select eyes 0 or 1.");
        }
    }

    /**
     * Encode VRPlayer into a buffer. Used internally
     * @param player VRPlayer to encode
     * @param buffer Buffer to encode into
     */
    public static void encode(VRPlayer player, PacketBuffer buffer) {
        VRData.encode(player.getHMD(), buffer);
        VRData.encode(player.getController0(), buffer);
        VRData.encode(player.getController1(), buffer);
        VRData.encode(player.getLeftEye(), buffer);
        VRData.encode(player.getRightEye(), buffer);
    }

    /**
     * Decode VRPlayer from a buffer. Used internally
     * @param buffer Buffer to decode from
     * @return VRPlayer from the buffer
     */
    public static VRPlayer decode(PacketBuffer buffer) {
        VRData hmd = VRData.decode(buffer);
        VRData c0 = VRData.decode(buffer);
        VRData c1 = VRData.decode(buffer);
        VRData leftEye = VRData.decode(buffer);
        VRData rightEye = VRData.decode(buffer);
        return new VRPlayer(hmd, c0, c1, leftEye, rightEye);
    }

}
