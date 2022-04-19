package net.blf02.vrapi.common.network.packets;

import net.blf02.vrapi.client.ServerHasAPI;
import net.blf02.vrapi.common.network.Network;
import net.blf02.vrapi.server.Tracker;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class VersionSyncPacket {

    public final String protocolVersion;

    public VersionSyncPacket(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public VersionSyncPacket() {
        this.protocolVersion = "GoodToGo!";
    }

    public static void encode(VersionSyncPacket packet, PacketBuffer buffer) {
        buffer.writeUtf(packet.protocolVersion);
    }

    public static VersionSyncPacket decode(PacketBuffer buffer) {
        return new VersionSyncPacket(buffer.readUtf());
    }

    public static void handle(VersionSyncPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            if (sender != null) {
                if (!message.protocolVersion.equals(Network.PROTOCOL_VERSION)) {
                    sender.connection.connection.disconnect(new StringTextComponent(
                            "Version mismatch! The server is on " + Network.PROTOCOL_VERSION + " but you're on " + message.protocolVersion + "!"));
                } else {
                    Tracker.playersInVR.add(sender.getGameProfile().getName());
                    Network.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new VersionSyncPacket());
                }
            } else { // Server says we're good to go!
                ServerHasAPI.serverHasAPI = true;
                ServerHasAPI.countForAPIResponse = false;
                ServerHasAPI.apiResponseCountdown = -1;
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
