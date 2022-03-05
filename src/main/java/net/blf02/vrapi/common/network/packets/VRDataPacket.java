package net.blf02.vrapi.common.network.packets;

import net.blf02.vrapi.data.VRPlayer;
import net.blf02.vrapi.server.Tracker;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class VRDataPacket {

    public final VRPlayer vrPlayer;

    public VRDataPacket(VRPlayer vrPlayer) {
        this.vrPlayer = vrPlayer;
    }

    public static void encode(VRDataPacket packet, PacketBuffer buffer) {
        VRPlayer.encode(packet.vrPlayer, buffer);
    }

    public static VRDataPacket decode(PacketBuffer buffer) {
        return new VRDataPacket(VRPlayer.decode(buffer));
    }

    public static void handle(VRDataPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            if (sender != null) {
                Tracker.playerToVR.put(sender.getGameProfile().getName(), msg.vrPlayer);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
