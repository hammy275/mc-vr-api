package net.blf02.vrapi.api.vrevent;

import net.blf02.vrapi.api.data.IVRPlayer;
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
public interface IVRPlayerTick {

    public PlayerEntity getPlayer();
    public TickEvent.Phase getPhase();
    public LogicalSide getSide();
    public IVRPlayer getVRPlayer();

}
