package net.blf02.vrapi.common;


import net.blf02.vrapi.server.Tracker;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonSubscriber {

    @SubscribeEvent
    public void onPlayerDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
        Tracker.playerToVR.remove(event.getPlayer().getGameProfile().getName());
    }
}
