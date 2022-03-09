package net.blf02.vrapi.debug;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.blf02.vrapi.event.VRPlayerTickEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DebugSubscriber {

    @SubscribeEvent
    public void onVRTick(VRPlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;
        PlayerEntity player = event.player;
        IVRPlayer vrPlayer = event.vrPlayer;
        if (player.tickCount % 20 == 0 && player.level.isClientSide) {
            for (int eye = 0; eye <= 1; eye++) {
                Vector3d pos = vrPlayer.getEye(eye).position();
                System.out.println("Eye: " + eye + "\tPos: " + pos);
                for (int i = 0; i < 10; i++) {
                    pos = pos.add(vrPlayer.getEye(eye).getLookAngle());
                    BasicParticleType type = eye == 0 ? ParticleTypes.DRIPPING_WATER : ParticleTypes.DRIPPING_LAVA;
                    player.level.addParticle(type, pos.x, pos.y, pos.z, 0, 0, 0);
                }
            }
        }
    }
}
