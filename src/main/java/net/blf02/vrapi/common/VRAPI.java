package net.blf02.vrapi.common;

import net.blf02.vrapi.api.IVRAPI;
import net.blf02.vrapi.api.data.IVRPlayer;
import net.blf02.vrapi.api.vrevent.IVRPlayerTick;
import net.blf02.vrapi.client.VRDataGrabber;
import net.blf02.vrapi.server.Tracker;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class VRAPI implements IVRAPI {

    public static final IVRAPI VRAPIInstance = new VRAPI();

    public static final List<Consumer<IVRPlayerTick>> vrPlayerTickers = new LinkedList<>();

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

    /**
     * Checks whether a player is in VR. Is built to be much faster than `getVRPlayer(player) != null`
     * @param player Player to check if in VR
     * @return true if the Player is in VR. False otherwise.
     */
    @Override
    public boolean playerInVR(PlayerEntity player) {
        if (player.level.isClientSide) {
            return VRDataGrabber.inVR();
        } else {
            return Tracker.playerToVR.containsKey(player.getGameProfile().getName());
        }
    }

    /**
     * Register a handler for the VRPlayerTick "VR event".
     * See `net.blf02.vrapi.api.vrevent.IVRPlayerTick for more info on when/how the consumer is called.
     *
     * @param handler A function that accepts an IVRPlayerTick.
     */
    @Override
    public void registerVRPlayerTickHandler(Consumer<IVRPlayerTick> handler) {
        vrPlayerTickers.add(handler);
    }
}
