package net.blf02.vrapi.common.network;

import dev.architectury.networking.NetworkChannel;
import net.blf02.vrapi.VRAPIMod;
import net.blf02.vrapi.common.Constants;
import net.minecraft.resources.ResourceLocation;

public class Network {

    public static final String PROTOCOL_VERSION = Constants.getNetworkVersion();

    public static final NetworkChannel CHANNEL =
            NetworkChannel.create(new ResourceLocation(VRAPIMod.MOD_ID, "vr_data"));
}
