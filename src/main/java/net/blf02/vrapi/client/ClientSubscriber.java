package net.blf02.vrapi.client;

import net.blf02.vrapi.common.network.Network;
import net.blf02.vrapi.common.network.packets.VRDataPacket;
import net.blf02.vrapi.common.network.packets.VersionSyncPacket;
import net.blf02.vrapi.data.VRPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
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
            if (ServerHasAPI.countForAPIResponse) {
                if (--ServerHasAPI.apiResponseCountdown < 1) {
                    ServerHasAPI.countForAPIResponse = false;
                    event.player.sendMessage(
                            new TextComponent("Server does not have API mod; modded VR features may not work!"),
                            event.player.getUUID());
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onLogin(ClientPlayerNetworkEvent.LoggedInEvent event) {
        ServerHasAPI.serverHasAPI = false;
        ServerHasAPI.countForAPIResponse = true;
        ServerHasAPI.apiResponseCountdown = 100;
        Network.INSTANCE.sendToServer(new VersionSyncPacket(Network.PROTOCOL_VERSION));
    }
}
