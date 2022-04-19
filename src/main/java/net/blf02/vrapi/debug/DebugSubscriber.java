package net.blf02.vrapi.debug;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DebugSubscriber {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.tickCount % 20 == 0) {
            String side = event.player.level.isClientSide ? "client" : "server";
            System.out.println(side + ": Both sides have VR: " + DebugPlugin.vrAPI.apiActive(event.player));
        }
    }
}
