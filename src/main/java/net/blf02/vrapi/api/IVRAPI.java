package net.blf02.vrapi.api;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

import javax.annotation.Nullable;

public interface IVRAPI {

    /**
     * Gets the API version as a string.
     * @return The version string using semantic versioning. Will always be of the form x.y.z, with no additional data.
     */
    public String getVersionString();

    /**
     * Gets the API version as an array of ints.
     * @return The version using semantic versioning in the form [major, minor, patch].
     */
    public int[] getVersionArray();

    /**
     * Returns whether the API is available to access on both the client-side and the server-side.
     * This does NOT indiciate if a player is in VR or not. Rather, it indicates if the API is open to use on both
     * logical sides.
     * @param player The player to check.
     * @return true if both the server and the client have the API installed. false otherwise.
     */
    public boolean apiActive(PlayerEntity player);

    /**
     * Gets the VRPlayer that represents all VR information about a player.
     * If this function returns null, the player either isn't in VR, or the server doesn't yet know that they're in
     * VR.
     *
     * @param player Player to get information
     * @return An IVRPlayer instance representing the player, or null based on the reasoning above.
     */
    public IVRPlayer getVRPlayer(PlayerEntity player);

    /**
     * Checks whether a player is in VR. Is built to be much faster than `getVRPlayer(player) != null`
     * @param player Player to check if in VR
     * @return true if the Player is in VR. False otherwise.
     */
    public boolean playerInVR(PlayerEntity player);

    /**
     * Triggers a haptic pulse/rumble for the controller. Much easier to understand version.
     *
     * NOTE: ONLY SPECIFY THE `player` PARAMETER IF THIS FUNCTION ISN'T BEING CALLED FROM THE CLIENT-SIDE!!!!
     *
     * @param controllerNum Controller number to rumble.
     * @param durationSeconds Number of seconds to rumble
     * @param player The player to rumble for. If null, this function does nothing server-side. If non-null, a packet
     *               is sent to the specified player to rumble server side.
     */
    public void triggerHapticPulse(int controllerNum, float durationSeconds, @Nullable ServerPlayerEntity player);

    /**
     * Triggers a haptic pulse/rumble for the controller.
     *
     * NOTE: ONLY SPECIFY THE `player` PARAMETER IF THIS FUNCTION ISN'T BEING CALLED FROM THE CLIENT-SIDE!!!!
     *
     * @param controllerNum Controller number to rumble
     * @param durationSeconds Number of seconds to rumble
     * @param frequency Literal frequency
     * @param amplitude Amplitude for rumble
     * @param delaySeconds How long until the rumble should run
     * @param player The player to rumble for. If null, this function does nothing server-side. If non-null, a packet
     *               is sent to the specified player to rumble server side.
     */
    public void triggerHapticPulse(int controllerNum, float durationSeconds, float frequency, float amplitude, float delaySeconds,
                                   @Nullable ServerPlayerEntity player);
}
