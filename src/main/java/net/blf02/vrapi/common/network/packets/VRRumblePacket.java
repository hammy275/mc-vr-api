package net.blf02.vrapi.common.network.packets;

import net.blf02.vrapi.common.VRAPI;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

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

    public static void encode(VRRumblePacket packet, PacketBuffer buffer) {
        buffer.writeInt(packet.controllerNum).writeFloat(packet.duration)
                .writeFloat(packet.frequency).writeFloat(packet.amplitude)
                .writeFloat(packet.delay);
    }

    public static VRRumblePacket decode(PacketBuffer buffer) {
        return new VRRumblePacket(buffer.readInt(), buffer.readFloat(),
                buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
    }

    public static void handle(VRRumblePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            if (sender == null) {  // From server to client
                VRAPI.VRAPIInstance.triggerHapticPulse(msg.controllerNum, msg.duration, msg.frequency,
                        msg.amplitude, msg.delay, null);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
