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
        IVRPlayer vrPlayerPostTick = VRAPI.VRAPIInstance.getVRPlayer(player);
        if (vrPlayerPostTick != null && player.tickCount % 20 == 0) {
            IVRPlayer vrPlayerPreTick = VRAPI.VRAPIInstance.getPreTickVRPlayer();
            IVRData left = vrPlayerPreTick.getController1();
            Vec3 posLeft = left.position().add(left.getLookAngle().multiply(5, 5, 5));
            player.level.addParticle(ParticleTypes.ANGRY_VILLAGER, posLeft.x, posLeft.y,
                    posLeft.z, 0, 0, 0);

            IVRPlayer renderPlayer = VRAPI.VRAPIInstance.getRenderVRPlayer();
            IVRData right = renderPlayer.getController0();
            Vec3 posRight = right.position().add(right.getLookAngle().multiply(5, 5, 5));
            player.level.addParticle(ParticleTypes.ANGRY_VILLAGER, posRight.x, posRight.y,
                    posRight.z, 0, 0, 0);


        }
    }
}