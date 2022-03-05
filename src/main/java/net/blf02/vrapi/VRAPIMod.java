package net.blf02.vrapi;

import net.blf02.vrapi.client.ClientSubscriber;
import net.blf02.vrapi.client.VRDataGrabber;
import net.blf02.vrapi.common.CommonSubscriber;
import net.blf02.vrapi.common.DebugSubscriber;
import net.blf02.vrapi.common.Constants;
import net.blf02.vrapi.common.network.Network;
import net.blf02.vrapi.common.network.packets.VRDataPacket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VRAPIMod.MOD_ID)
public class VRAPIMod {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "vrapi";

    public VRAPIMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClientSubscriber());
        MinecraftForge.EVENT_BUS.register(new CommonSubscriber());
        if (Constants.doDebugging) {
            MinecraftForge.EVENT_BUS.register(new DebugSubscriber());
        }

        Constants.init();
        VRDataGrabber.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Network.INSTANCE.registerMessage(1, VRDataPacket.class, VRDataPacket::encode, VRDataPacket::decode,
                VRDataPacket::handle);
    }
}
