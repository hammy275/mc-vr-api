package net.blf02.vrapi.vrevent;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.blf02.vrapi.api.vrevent.IVRPlayerTick;
import net.blf02.vrapi.common.VRAPI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;


/** A "VR event" that is called when TickEvent.PlayerTickEvent is called.
 *
 * It's practically identical to it, but it is only called for players in VR.
 *
 * You can register a function that will run under the same conditions as TickEvent.PlayerTickEvent
 * using `net.blf02.vrapi.api.IVRAPI.registerVRPlayerTickHandler`
 *
 */
public class VRPlayerTick implements IVRPlayerTick {

    public final PlayerEntity player;
    public final TickEvent.Phase phase;
    public final LogicalSide side;
    public final IVRPlayer vrPlayer;

    public VRPlayerTick(PlayerEntity player, TickEvent.Phase phase,
                        LogicalSide side) {
        this.player = player;
        this.phase = phase;
        this.side = side;
        if (VRAPI.VRAPIInstance.playerInVR(player)) {
            this.vrPlayer = VRAPI.VRAPIInstance.getVRPlayer(player);
            VRAPI.vrPlayerTickers.forEach((consumer) -> {
                consumer.accept(this);
            });
        } else {
            this.vrPlayer = null;
        }
    }

    @Override
    public PlayerEntity getPlayer() {
        return this.player;
    }

    @Override
    public TickEvent.Phase getPhase() {
        return this.phase;
    }

    @Override
    public LogicalSide getSide() {
        return this.side;
    }

    @Override
    public IVRPlayer getVRPlayer() {
        return this.vrPlayer;
    }
}
