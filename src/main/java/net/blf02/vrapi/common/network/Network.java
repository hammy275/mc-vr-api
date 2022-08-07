package net.blf02.vrapi.common.network;

import net.blf02.vrapi.VRAPIMod;
import net.blf02.vrapi.common.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Network {

    public static final String PROTOCOL_VERSION = Constants.getNetworkVersion();

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(VRAPIMod.MOD_ID, "vr_data"),
            () -> PROTOCOL_VERSION,
            (ignored) -> true,
            (ignored) -> true
    );
}
