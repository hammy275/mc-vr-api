package net.blf02.vrapi.common.network.packets;

import dev.architectury.networking.NetworkManager;
import net.blf02.vrapi.data.VRPlayer;
import net.blf02.vrapi.server.Tracker;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class VRDataPacket {

    public final VRPlayer vrPlayer;

    public VRDataPacket(VRPlayer vrPlayer) {
        this.vrPlayer = vrPlayer;
    }

    public static void encode(VRDataPacket packet, FriendlyByteBuf buffer) {
        VRPlayer.encode(packet.vrPlayer, buffer);
    }

    public static VRDataPacket decode(FriendlyByteBuf buffer) {
        return new VRDataPacket(VRPlayer.decode(buffer));
    }

    public void handle(Supplier<NetworkManager.PacketContext> ctx) {
        ctx.get().queue(() -> {
            Player senderP = ctx.get().getPlayer();
            if (senderP instanceof ServerPlayer sender) {
                Tracker.playerToVR.put(sender.getGameProfile().getName(), this.vrPlayer);
            }
        });
    }
}
