package net.blf02.vrapi.debug;

import net.blf02.vrapi.common.VRAPI;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

public class DebugSubscriber {

    public static void onPlayerTick(Player player) {
        if (VRAPI.VRAPIInstance.playerInVR(player)) {
            VRAPI.VRAPIInstance.triggerHapticPulse(1, 0.05f,
                    null);
            player.sendMessage(new TextComponent(
                    (player.isLocalPlayer() ? "Client" : "Server") + " reports in VR status: "
                    + VRAPI.VRAPIInstance.playerInVR(player)
            ), player.getUUID());
        }
    }
}