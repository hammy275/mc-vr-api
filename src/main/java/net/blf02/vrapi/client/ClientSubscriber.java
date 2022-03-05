package net.blf02.vrapi.client;

import net.blf02.vrapi.api.data.VRPlayer;
import net.blf02.vrapi.common.network.Network;
import net.blf02.vrapi.common.network.packets.VRDataPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ClientSubscriber {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.level.isClientSide && event.phase == TickEvent.Phase.END) {
            VRPlayer player = VRDataGrabber.getVRPlayer();
            if (player != null) {
                Network.INSTANCE.sendToServer(new VRDataPacket(player));
            }
        }
    }
}
