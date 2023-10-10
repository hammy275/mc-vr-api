package net.blf02.vrapi.debug;

import net.blf02.vrapi.api.data.IVRData;
import net.blf02.vrapi.common.VRAPI;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class DebugSubscriber {

    public static void onPlayerTick(Player player) {
        if (VRAPI.VRAPIInstance.playerInVR(player)) {
            IVRData data = VRAPI.VRAPIInstance.getVRPlayer(player).getHMD();
            Vec3 pos = data.position().add(data.getLookAngle());
            player.level().addParticle(ParticleTypes.SNOWFLAKE,
                    pos.x, pos.y, pos.z,
                    0.01, 0.01, 0.01);
            data = VRAPI.VRAPIInstance.getVRPlayer(player).getController1();
            pos = data.position().add(data.getLookAngle());
            player.level().addParticle(ParticleTypes.ANGRY_VILLAGER,
                    pos.x, pos.y, pos.z,
                    0.01, 0.01, 0.01);
            data = VRAPI.VRAPIInstance.getVRPlayer(player).getController0();
            pos = data.position().add(data.getLookAngle());
            player.level().addParticle(ParticleTypes.FALLING_WATER,
                    pos.x, pos.y, pos.z,
                    0.01, 0.01, 0.01);

            if (player.tickCount % 20 == 0) {
                if (player.level().isClientSide) {
                    VRAPI.VRAPIInstance.triggerHapticPulse(0, 0.05f, null);
                } else {
                    VRAPI.VRAPIInstance.triggerHapticPulse(1, 0.05f, (ServerPlayer) player);
                }
            }
        }
    }
}
