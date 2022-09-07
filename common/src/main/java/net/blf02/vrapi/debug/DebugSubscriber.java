package net.blf02.vrapi.debug;

import net.blf02.vrapi.api.data.IVRData;
import net.blf02.vrapi.api.data.IVRPlayer;
import net.blf02.vrapi.common.VRAPI;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class DebugSubscriber {

    public static void onPlayerTick(Player player) {
        if (!player.level.isClientSide) return;
        IVRPlayer vrPlayer = VRAPI.VRAPIInstance.getVRPlayer(player);
        if (vrPlayer != null && player.tickCount % 20 == 0) {
            for (int i = 0; i <= 1; i++) {
                IVRData con = vrPlayer.getController(i);
                Vec3 pos = con.position().add(con.getLookAngle().multiply(5, 5, 5));
                player.level.addParticle(ParticleTypes.ANGRY_VILLAGER, pos.x, pos.y,
                        pos.z, 0, 0, 0);
            }
            IVRData head = vrPlayer.getHMD();
            player.level.addParticle(ParticleTypes.ASH, head.position().x, head.position().y,
                    head.position().z, 0, 0, 0);

        }
    }
}
