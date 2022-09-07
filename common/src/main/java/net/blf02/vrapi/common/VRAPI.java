package net.blf02.vrapi.common;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.blf02.vrapi.api.IVRAPI;
import net.blf02.vrapi.api.data.IVRPlayer;
import net.blf02.vrapi.client.ReflectionConstants;
import net.blf02.vrapi.client.ServerHasAPI;
import net.blf02.vrapi.client.VRDataGrabber;
import net.blf02.vrapi.common.network.Network;
import net.blf02.vrapi.common.network.packets.VRRumblePacket;
import net.blf02.vrapi.server.Tracker;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class VRAPI implements IVRAPI {

    public static final IVRAPI VRAPIInstance = new VRAPI();

    /**
     * Gets the API version as a string.
     * @return The version string using semantic versioning. Will always be of the form x.y.z, with no additional data.
     */
    @Override
    public String getVersionString() {
        return Constants.getVersion();
    }

    /**
     * Gets the API version as an array of ints.
     * @return The version using semantic versioning in the form [major, minor, patch].
     */
    @Override
    public int[] getVersionArray() {
        return Constants.version;
    }

    /**
     * Returns whether the API is available to access on both the client-side and the server-side.
     * This does NOT indiciate if a player is in VR or not. Rather, it indicates if the API is open to use on both
     * logical sides.
     * @param player The player to check.
     * @return true if both the server and the client have the API installed. false otherwise.
     */
    @Override
    public boolean apiActive(Player player) {
        if (player.level.isClientSide) {
            return ServerHasAPI.serverHasAPI;
        } else {
            return Tracker.playersInVR.contains(player.getGameProfile().getName());
        }
    }

    /**
     * Gets the VRPlayer that represents all VR information about a player.
     * If this function returns null, the player either isn't in VR, or the server doesn't yet know that they're in
     * VR.
     *
     * @param player Player to get information
     * @return A VRPlayer instance representing the player, or null based on the reasoning above.
     */
    @Nullable
    public IVRPlayer getVRPlayer(Player player) {
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
    public boolean playerInVR(Player player) {
        if (player.level.isClientSide) {
            return VRDataGrabber.inVR();
        } else {
            return Tracker.playerToVR.containsKey(player.getGameProfile().getName());
        }
    }

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
    public void triggerHapticPulse(int controllerNum, float durationSeconds, @Nullable ServerPlayer player) {
        // Constants come from the original Vivecraft code
        triggerHapticPulse(controllerNum, durationSeconds, 160, 1, 0, player);
    }

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
                                   @Nullable ServerPlayer player) {
        if (Platform.getEnvironment() == Env.CLIENT) {
            try {
                VRDataGrabber.MCVR_triggerHapticPulse.invoke(VRDataGrabber.Minecraft_vr_Instance,
                        ReflectionConstants.ControllerType_ENUMS[controllerNum], durationSeconds, frequency, amplitude, delaySeconds);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Could not run triggerHapticPulse function. Not sure why, though...");
            }
        } else if (player != null) {
            Network.CHANNEL.sendToPlayer(player,
                    new VRRumblePacket(controllerNum, durationSeconds, frequency, amplitude, delaySeconds));
        }
    }
}
