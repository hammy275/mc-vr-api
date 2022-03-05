package net.blf02.vrapi.api.data;

import net.minecraft.entity.player.PlayerEntity;

public class VRPlayer {

    protected final VRData hmd;
    protected final VRData controller0;
    protected final VRData controller1;
    protected final PlayerEntity player;

    public VRPlayer(VRData hmd, VRData controller0, VRData controller1, PlayerEntity player) {
        this.hmd = hmd;
        this.controller0 = controller0;
        this.controller1 = controller1;
        this.player = player;
    }

    public VRData getHMD() {
        return this.hmd;
    }

    public VRData getController0() {
        return this.controller0;
    }

    public VRData getController1() {
        return this.controller1;
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }

    public VRData getController(int controller) {
        if (controller != 0 && controller != 1) {
            throw new IllegalArgumentException("Controller " + controller + " invalid! You can only select controllers 0 or 1.");
        }
        return controller == 0 ? getController0() : getController1();
    }

}
