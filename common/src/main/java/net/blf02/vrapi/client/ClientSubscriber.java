package net.blf02.vrapi.client;

import net.blf02.vrapi.common.network.Network;
import net.blf02.vrapi.common.network.packets.VRDataPacket;
import net.blf02.vrapi.common.network.packets.VersionSyncPacket;
import net.blf02.vrapi.data.VRPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

public class ClientSubscriber {

    public static void onPlayerTick(Player player) {
        if (player.level.isClientSide) {
            VRPlayer vrPlayer = VRDataGrabber.getVRPlayer();
            if (vrPlayer != null) {
                Network.CHANNEL.sendToServer(new VRDataPacket(vrPlayer));
            }
            if (ServerHasAPI.countForAPIResponse) {
                if (--ServerHasAPI.apiResponseCountdown < 1) {
                    ServerHasAPI.countForAPIResponse = false;
                    player.sendMessage(
                            new TextComponent("Server does not have API mod; modded VR features may not work!"),
                            player.getUUID());
                }
            }
        }
    }

    public static void onLogin(Player player) {
        ServerHasAPI.serverHasAPI = false;
        ServerHasAPI.countForAPIResponse = true;
        ServerHasAPI.apiResponseCountdown = 100;
        Network.CHANNEL.sendToServer(new VersionSyncPacket(Network.PROTOCOL_VERSION));
    }
}
