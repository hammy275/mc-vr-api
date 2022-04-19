package net.blf02.vrapi;

import net.blf02.vrapi.client.ClientSubscriber;
import net.blf02.vrapi.client.VRDataGrabber;
import net.blf02.vrapi.common.APIProviderInit;
import net.blf02.vrapi.common.CommonSubscriber;
import net.blf02.vrapi.common.Constants;
import net.blf02.vrapi.common.network.Network;
import net.blf02.vrapi.common.network.packets.VRDataPacket;
import net.blf02.vrapi.common.network.packets.VRRumblePacket;
import net.blf02.vrapi.common.network.packets.VersionSyncPacket;
import net.blf02.vrapi.debug.DebugSubscriber;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
        // Only bother to grab VR Data when on the client-side
        if (FMLEnvironment.dist == Dist.CLIENT) {
            VRDataGrabber.init();
        }

        // Don't show red X if the server doesn't have the API but we do.
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST,
                () -> new ImmutablePair<>(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Network.INSTANCE.registerMessage(1, VRDataPacket.class, VRDataPacket::encode, VRDataPacket::decode,
                VRDataPacket::handle);
        Network.INSTANCE.registerMessage(2, VRRumblePacket.class, VRRumblePacket::encode, VRRumblePacket::decode,
                VRRumblePacket::handle);
        Network.INSTANCE.registerMessage(3, VersionSyncPacket.class, VersionSyncPacket::encode, VersionSyncPacket::decode,
                VersionSyncPacket::handle);
        APIProviderInit.init();
    }
}
