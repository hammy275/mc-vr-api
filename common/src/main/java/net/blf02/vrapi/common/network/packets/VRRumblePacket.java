package net.blf02.vrapi.common.network.packets;

import dev.architectury.networking.NetworkManager;
import net.blf02.vrapi.common.VRAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class VRRumblePacket {

    protected final int controllerNum;
    protected final float duration;
    protected final float frequency;
    protected final float amplitude;
    protected final float delay;

    public VRRumblePacket(int controllerNum, float durationSeconds, float frequency, float amplitude, float delaySeconds) {
        this.controllerNum = controllerNum;
        this.duration = durationSeconds;
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.delay = delaySeconds;
    }

    public static void encode(VRRumblePacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.controllerNum).writeFloat(packet.duration)
                .writeFloat(packet.frequency).writeFloat(packet.amplitude)
                .writeFloat(packet.delay);
    }

    public static VRRumblePacket decode(FriendlyByteBuf buffer) {
        return new VRRumblePacket(buffer.readInt(), buffer.readFloat(),
                buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
    }

    public void handle(Supplier<NetworkManager.PacketContext> ctx) {
        ctx.get().queue(() -> {
            Player senderP = ctx.get().getPlayer();
            if (!(senderP instanceof ServerPlayer)) {  // From server to client
                VRAPI.VRAPIInstance.triggerHapticPulse(this.controllerNum, this.duration, this.frequency,
                        this.amplitude, this.delay, null);
            }
        });
    }
}
