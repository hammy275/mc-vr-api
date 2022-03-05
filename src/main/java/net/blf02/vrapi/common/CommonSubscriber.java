package net.blf02.vrapi.common;

import net.blf02.vrapi.api.data.VRData;
import net.blf02.vrapi.api.data.VRPlayer;
import net.blf02.vrapi.client.VRDataGrabber;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.InvocationTargetException;

@Mod.EventBusSubscriber
public class CommonSubscriber {

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        try {
            // Watch as I reach across logical sides... for testing purposes!
            VRPlayer player = VRDataGrabber.getVRPlayer();
            if (player == null) return;
            PlayerEntity p = player.getPlayer();
            if (p == null) return;
            VRData hmd = player.getHMD();
            VRData controller0 = player.getController0();
            VRData controller1 = player.getController1();
            p.level.addParticle(ParticleTypes.ANGRY_VILLAGER,
                    hmd.getPosition().x, hmd.getPosition().y, hmd.getPosition().z,
                    0, 0, 0);
            p.level.addParticle(ParticleTypes.ANGRY_VILLAGER,
                    controller0.getPosition().x, controller0.getPosition().y, controller0.getPosition().z,
                    0, 0, 0);
            p.level.addParticle(ParticleTypes.ANGRY_VILLAGER,
                    controller1.getPosition().x, controller1.getPosition().y, controller1.getPosition().z,
                    0, 0, 0);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
