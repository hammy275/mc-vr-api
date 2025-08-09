package net.blf02.forge;

import net.blf02.vrapi.VRAPIMod;
import net.blf02.vrapi.common.Plat;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mod(VRAPIMod.MOD_ID)
public class VRAPIForge {

    public static List<Consumer<Void>> setups = new ArrayList<>();

    public VRAPIForge(FMLJavaModLoadingContext context) {
        Plat.INSTANCE = new PlatformImpl();
        FMLCommonSetupEvent.getBus(context.getModBusGroup()).addListener(this::commonSetup);
        // Don't show red X if the server doesn't have the API but we do.
        context.registerExtensionPoint(IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));

        if (Plat.INSTANCE.isClient()) {

            RegisterKeyMappingsEvent.getBus(context.getModBusGroup()).addListener(this::registerKeyMappings);
        }

        VRAPIMod.init();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            setups.forEach(setup -> setup.accept(null));
            APIProviderInit.init();
        });
    }

    private void registerKeyMappings(RegisterKeyMappingsEvent event) {
        PlatformImpl.keyMappingsToRegister.forEach(o -> event.register((KeyMapping) o));
    }
}
