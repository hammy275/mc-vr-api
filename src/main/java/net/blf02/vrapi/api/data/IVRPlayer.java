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
}
