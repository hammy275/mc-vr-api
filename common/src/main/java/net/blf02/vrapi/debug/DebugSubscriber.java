package net.blf02.vrapi.debug;

import net.blf02.vrapi.common.VRAPI;
import net.minecraft.world.entity.player.Player;

public class DebugSubscriber {

    public static void onPlayerTick(Player player) {
        if (VRAPI.VRAPIInstance.playerInVR(player)) {
            String clientStr = player.level.isClientSide ? "client" : "server";
            System.out.println("Side " + clientStr + "'s seated status: " + VRAPI.VRAPIInstance.isSeated(player));
            System.out.println("Side " + clientStr + "'s left handed status: " + VRAPI.VRAPIInstance.isLeftHanded(player));
        }
    }
}
