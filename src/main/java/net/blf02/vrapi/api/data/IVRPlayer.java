package net.blf02.vrapi.api.data;

/**
 * IVRPlayer contains all the information related to a player in VR.
 */
public interface IVRPlayer {

    /**
     * Get HMD information
     * @return IVRData representing the position and rotation of the HMD
     */
    public IVRData getHMD();

    /**
     * Get Controller 0 information
     * @return IVRData representing the position and rotation of Controller 0 (usually the right hand)
     */
    public IVRData getController0();

    /**
     * Get Controller 1 information
     * @return IVRData representing the position and rotation of Controller 0 (usually the right hand)
     */
    public IVRData getController1();

    /**
     * Get Controller information
     * @param controller The controller number to retrieve (0 or 1).
     * @return IVRData representing the requested controller
     */
    public IVRData getController(int controller);

    /**
     * Get Left Eye information
     * @return IVRData representing the position and look direction of the left eye
     */
    public IVRData getLeftEye();

    /**
     * Get Right Eye information
     * @return IVRData representing the position and look direction of the right eye
     */
    public IVRData getRightEye();

    /**
     * Get information for an eye.
     * @param eye Eye number to get. 0 for left, 1 for right.
     * @return IVRData representing the eye.
     */
    public IVRData getEye(int eye);
}
