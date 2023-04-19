package net.blf02.vrapi.common.network.packets;

import dev.architectury.networking.NetworkManager;
import net.blf02.vrapi.client.ServerHasAPI;
import net.blf02.vrapi.common.network.Network;
import net.blf02.vrapi.server.Tracker;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class VersionSyncPacket {

    public final String protocolVersion;

    public VersionSyncPacket(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public VersionSyncPacket() {
        this.protocolVersion = "GoodToGo!";
    }

    public static void encode(VersionSyncPacket packet, FriendlyByteBuf buffer) {
        buffer.writeUtf(packet.protocolVersion);
    }

    public static VersionSyncPacket decode(FriendlyByteBuf buffer) {
        return new VersionSyncPacket(buffer.readUtf());
    }

    public void handle(Supplier<NetworkManager.PacketContext> ctx) {
        ctx.get().queue(() -> {
            Player senderP = ctx.get().getPlayer();
            if (senderP instanceof ServerPlayer sender) {
                if (!this.protocolVersion.equals(Network.PROTOCOL_VERSION)) {
                    sender.connection.disconnect(Component.translatable(
                            "message.vrapi.version_mismatch", Network.PROTOCOL_VERSION, this.protocolVersion));
                } else {
                    Tracker.playersInVR.add(sender.getGameProfile().getName());
                    Network.CHANNEL.sendToPlayer(sender, new VersionSyncPacket());
                }
            } else { // Server says we're good to go!
                ServerHasAPI.serverHasAPI = true;
                ServerHasAPI.countForAPIResponse = false;
                ServerHasAPI.apiResponseCountdown = -1;
            }
        });
    }
}
