package net.blf02.vrapi.debug;

import net.blf02.vrapi.api.data.IVRData;
import net.blf02.vrapi.common.VRAPI;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;

public class DebugSubscriber {

    public static void onPlayerTick(Player player) {
        if (VRAPI.VRAPIInstance.playerInVR(player)) {
            IVRData hmd = VRAPI.VRAPIInstance.getVRPlayer(player).getHMD();
            player.level().addParticle(ParticleTypes.SNOWFLAKE,
                    hmd.position().x, hmd.position().y, hmd.position().z,
                    0.01, 0.01, 0.01);
            IVRData left = VRAPI.VRAPIInstance.getVRPlayer(player).getController1();
            player.level().addParticle(ParticleTypes.ANGRY_VILLAGER,
                    left.position().x, left.position().y, left.position().z,
                    0.01, 0.01, 0.01);
            IVRData right = VRAPI.VRAPIInstance.getVRPlayer(player).getController0();
            player.level().addParticle(ParticleTypes.FALLING_WATER,
                    right.position().x, right.position().y, right.position().z,
                    0.01, 0.01, 0.01);
        }
    }
}
