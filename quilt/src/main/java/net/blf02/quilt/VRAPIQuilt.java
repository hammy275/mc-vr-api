package net.blf02.quilt;

import net.blf02.vrapi.VRAPIMod;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class VRAPIQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        VRAPIMod.init();
    }
}
