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
}
