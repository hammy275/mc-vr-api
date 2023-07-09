package net.blf02.vrapi.common;


import net.blf02.vrapi.server.Tracker;
import net.minecraft.world.entity.player.Player;

public class CommonSubscriber {

    public static void onPlayerDisconnect(Player player) {
        // Also called from LeftVRPacket
        Tracker.playerToVR.remove(player.getGameProfile().getName());
        Tracker.playersInVR.remove(player.getGameProfile().getName());
    }
}
