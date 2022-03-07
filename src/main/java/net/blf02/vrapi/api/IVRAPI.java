package net.blf02.vrapi.api;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.minecraft.entity.player.PlayerEntity;

public interface IVRAPI {

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
}
