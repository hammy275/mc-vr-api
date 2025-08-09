package net.blf02.neoforge;

import net.blf02.vrapi.VRAPIMod;
import net.blf02.vrapi.common.Plat;
import net.blf02.vrapi.common.network.Network;
import net.minecraft.client.KeyMapping;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mod(VRAPIMod.MOD_ID)
public class VRAPINeoForge {

    public static List<Consumer<Void>> setups = new ArrayList<>();

    public VRAPINeoForge(IEventBus modBus) {
        Plat.INSTANCE = new PlatformImpl();
        modBus.addListener(this::commonSetup);

        if (Plat.INSTANCE.isClient()) {
            modBus.addListener(this::registerKeyMappings);
            modBus.addListener(this::registerClientPayloadHandlers);
        }
        modBus.addListener((RegisterPayloadHandlersEvent event) -> {
            PayloadRegistrar registrar = event.registrar(VRAPIMod.MOD_ID);
            registrar.optional().playBidirectional(BufferPacket.ID, BufferPacket.CODEC,
                    (packet, payloadContext) -> payloadContext.enqueueWork(() -> Network.CHANNEL.doReceive((ServerPlayer) payloadContext.player(), packet.buffer())));
        });

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

    private void registerClientPayloadHandlers(RegisterClientPayloadHandlersEvent event) {
        event.register(BufferPacket.ID,
                (packet, payloadContext) -> payloadContext.enqueueWork(() -> Network.CHANNEL.doReceive(null, packet.buffer())));
    }
}
