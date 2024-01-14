package net.blf02.neoforge;

import net.blf02.vrapi.VRAPIMod;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(VRAPIMod.MOD_ID)
public class VRAPINeoForge {
    public VRAPINeoForge() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        // Don't show red X if the server doesn't have the API but we do.
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
        VRAPIMod.init();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        APIProviderInit.init();
    }
}
