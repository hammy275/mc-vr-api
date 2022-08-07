package net.blf02.vrapi.event;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.blf02.vrapi.common.VRAPI;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

public class VRPlayerTickEvent extends Event {

    /**
     * Contains the player this event is fired for
     */
    public final Player player;

    /**
     * Contains the phase from TickEvent.PlayerTickEvent this is fired on.
     */
    public final TickEvent.Phase phase;

    /**
     * Contains the logical side this event is fired on.
     */
    public final LogicalSide side;

    /**
     * Contains the IVRPlayer instance for the player
     */
    public final IVRPlayer vrPlayer;

    public VRPlayerTickEvent(Player player, TickEvent.Phase phase, LogicalSide side) {
        this.player = player;
        this.phase = phase;
        this.side = side;
        if (VRAPI.VRAPIInstance.playerInVR(player) && VRAPI.VRAPIInstance.apiActive(player)) {
            vrPlayer = VRAPI.VRAPIInstance.getVRPlayer(player);
        } else {
            vrPlayer = null;
        }
    }


}
