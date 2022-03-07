package net.blf02.vrapi.event;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.blf02.vrapi.common.VRAPI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

public class VRPlayerTickEvent extends Event {

    public final PlayerEntity player;
    public final TickEvent.Phase phase;
    public final LogicalSide side;
    public final IVRPlayer vrPlayer;

    public VRPlayerTickEvent(PlayerEntity player, TickEvent.Phase phase, LogicalSide side) {
        this.player = player;
        this.phase = phase;
        this.side = side;
        if (VRAPI.VRAPIInstance.playerInVR(player)) {
            vrPlayer = VRAPI.VRAPIInstance.getVRPlayer(player);
        } else {
            vrPlayer = null;
        }
    }


}
