package net.blf02.vrapi.common;


import net.blf02.vrapi.event.VRPlayerTickEvent;
import net.blf02.vrapi.server.Tracker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonSubscriber {

    @SubscribeEvent
    public void onPlayerDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
        Tracker.playerToVR.remove(event.getPlayer().getGameProfile().getName());
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        VRPlayerTickEvent vrEvent = new VRPlayerTickEvent(event.player, event.phase, event.side);
        if (vrEvent.vrPlayer != null) {
            MinecraftForge.EVENT_BUS.post(vrEvent);
        }
    }
}
