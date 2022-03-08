package net.blf02.vrapi.debug;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.blf02.vrapi.event.VRPlayerTickEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DebugSubscriber {

    @SubscribeEvent
    public void onVRTick(VRPlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;
        PlayerEntity player = event.player;
        IVRPlayer vrPlayer = event.vrPlayer;
        if (player.tickCount % 20 == 0 && !player.level.isClientSide) {
            DebugPlugin.vrAPI.triggerHapticPulse(0, 0.5f, (ServerPlayerEntity) player);
        }
    }
}
