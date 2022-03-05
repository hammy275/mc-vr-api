package net.blf02.vrapi.common;

import net.blf02.vrapi.api.IVRAPI;
import net.blf02.vrapi.api.data.IVRPlayer;
import net.blf02.vrapi.client.VRDataGrabber;
import net.blf02.vrapi.server.Tracker;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;

public class VRAPI implements IVRAPI {

    public static final IVRAPI VRAPIInstance = new VRAPI();

    /**
     * Gets the VRPlayer that represents all VR information about a player.
     * If this function returns null, the player either isn't in VR, or the server doesn't yet know that they're in
     * VR.
     *
     * @param player Player to get information
     * @return A VRPlayer instance representing the player, or null based on the reasoning above.
     */
    @Nullable
    public IVRPlayer getVRPlayer(PlayerEntity player) {
        if (player.level.isClientSide) {
            return VRDataGrabber.getVRPlayer();
        } else {
            return Tracker.playerToVR.get(player.getGameProfile().getName());
        }
    }
}
