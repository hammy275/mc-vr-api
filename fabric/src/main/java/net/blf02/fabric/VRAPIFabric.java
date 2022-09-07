package net.blf02.fabric;

import net.blf02.vrapi.VRAPIMod;
import net.fabricmc.api.ModInitializer;

public class VRAPIFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VRAPIMod.init();
    }
}
