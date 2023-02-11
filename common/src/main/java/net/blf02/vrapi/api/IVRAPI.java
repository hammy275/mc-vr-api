package net.blf02.vrapi.api;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

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
    public boolean apiActive(Player player);

    /**
     * Gets the VRPlayer that represents all VR information about a player. Gets the data after the game tick.
     * If this function returns null, the player either isn't in VR, or the server doesn't yet know that they're in
     * VR.
     *
     * @param player Player to get information
     * @return An IVRPlayer instance representing the player, or null based on the reasoning above.
     */
    public IVRPlayer getVRPlayer(Player player);

    /**
     * Gets the VRPlayer that represents all VR information about a player. Gets the data before the game tick.
     * If this function returns null, the player isn't in VR.
     * THIS FUNCTION IS ONLY AVAILABLE CLIENT-SIDE!
     *
     * @return An IVRPlayer instance representing the player, or null based on the reasoning above.
     */
    public IVRPlayer getPreTickVRPlayer();

    /**
     * Gets the VRPlayer that represents all VR information about a player. Gets the data from before rendering a frame.
     * If this function returns null, the player isn't in VR.
     * THIS FUNCTION IS ONLY AVAILABLE CLIENT-SIDE!
     *
     * @return An IVRPlayer instance representing the player, or null based on the reasoning above.
     */
    public IVRPlayer getRenderVRPlayer();

    /**
     * Gets the VRPlayer that represents all VR information about a player relative to the room, rather than in
     * Minecraft coordinates. Gets the data from before a game tick.
     * If this function returns null, the player isn't in VR.
     * THIS FUNCTION IS ONLY AVAILABLE CLIENT-SIDE!
     *
     * @return An IVRPlayer instance representing the player respective to the room, or null if they're not in VR.
     */
    public IVRPlayer getPreTickRoomVRPlayer();

    /**
     * Gets the VRPlayer that represents all VR information about a player relative to the room, rather than in
     * Minecraft coordinates. Gets the data from after a game tick.
     * If this function returns null, the player isn't in VR.
     * THIS FUNCTION IS ONLY AVAILABLE CLIENT-SIDE!
     *
     * @return An IVRPlayer instance representing the player respective to the room, or null if they're not in VR.
     */
    public IVRPlayer getPostTickRoomVRPlayer();

    /**
     * Checks whether a player is in VR. Is built to be much faster than `getVRPlayer(player) != null`
     * @param player Player to check if in VR
     * @return true if the Player is in VR. False otherwise.
     */
    public boolean playerInVR(Player player);

    /**
     * Triggers a haptic pulse/rumble for the controller. Much easier to understand version.
     * NOTE: ONLY SPECIFY THE `player` PARAMETER IF THIS FUNCTION ISN'T BEING CALLED FROM THE CLIENT-SIDE!!!!
     *
     * @param controllerNum Controller number to rumble.
     * @param durationSeconds Number of seconds to rumble
     * @param player The player to rumble for. If null, this function does nothing server-side. If non-null, a packet
     *               is sent to the specified player to rumble server side.
     */
    public void triggerHapticPulse(int controllerNum, float durationSeconds, @Nullable ServerPlayer player);

    /**
     * Triggers a haptic pulse/rumble for the controller.
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
                                   @Nullable ServerPlayer player);

    /**
     * Returns whether the player is currently playing in seated mode.
     * @param player The player to check.
     * @return true if the player is playing in seated mode. false otherwise.
     */
    public boolean isSeated(Player player);


    /**
     * Returns whether the player is currently playing in left-handed mode.
     * @param player The player to check.
     * @return true if the player has left-handed mode enabled. false otherwise.
     */
    public boolean isLeftHanded(Player player);
}
