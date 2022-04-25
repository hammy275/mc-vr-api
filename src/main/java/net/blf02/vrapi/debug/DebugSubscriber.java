package net.blf02.vrapi.debug;

import net.blf02.vrapi.event.VRPlayerTickEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DebugSubscriber {

    @SubscribeEvent
    public void onPlayerTick(VRPlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.tickCount % 20 == 0) {
            System.out.println(event.vrPlayer.getController0().getRoll());
        }
    }
}
