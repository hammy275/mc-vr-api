package net.blf02.vrapi.debug;

import net.blf02.vrapi.api.data.IVRData;
import net.blf02.vrapi.event.VRPlayerTickEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DebugSubscriber {

    @SubscribeEvent
    public void onPlayerTick(VRPlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!event.player.level.isClientSide) return;
        if (event.player.tickCount % 20 == 0) {
            for (int i = 0; i <= 1; i++) {
                IVRData con = event.vrPlayer.getController(i);
                Vec3 pos = con.position().add(con.getLookAngle().multiply(5, 5, 5));
                event.player.level.addParticle(ParticleTypes.ANGRY_VILLAGER, pos.x, pos.y,
                        pos.z, 0, 0, 0);
            }
            IVRData head = event.vrPlayer.getHMD();
            event.player.level.addParticle(ParticleTypes.ASH, head.position().x, head.position().y,
                    head.position().z, 0, 0, 0);

        }
    }
}
