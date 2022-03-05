package net.blf02.vrapi.common;

import net.blf02.vrapi.api.VRAPI;
import net.blf02.vrapi.api.data.VRPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DebugSubscriber {

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;
        PlayerEntity player = event.player;
        VRPlayer vrPlayer = VRAPI.getVRPlayer(player);
        if (vrPlayer == null) return;
        for (int i = 0; i < 20; i++) {
            Vector3d toCheckHMD = vrPlayer.getHMD().getPosition().add(vrPlayer.getHMD().getLookVec().multiply(i, i, i));
            Vector3d toCheck0 = vrPlayer.getController0().getPosition().add(vrPlayer.getController0().getLookVec().multiply(i, i, i));
            Vector3d toCheck1 = vrPlayer.getController1().getPosition().add(vrPlayer.getController1().getLookVec().multiply(i, i, i));

            if (player.level.isClientSide) {
                player.level.addParticle(ParticleTypes.SMOKE, toCheckHMD.x, toCheckHMD.y, toCheckHMD.z, 0, 0, 0);
                player.level.addParticle(ParticleTypes.ANGRY_VILLAGER, toCheck0.x, toCheck0.y, toCheck0.z, 0, 0, 0);
                player.level.addParticle(ParticleTypes.DRIPPING_LAVA, toCheck1.x, toCheck1.y, toCheck1.z, 0, 0, 0);
            } else {
                player.level.removeBlock(new BlockPos(toCheckHMD), true);
                player.level.removeBlock(new BlockPos(toCheck0), true);
                player.level.removeBlock(new BlockPos(toCheck1), true);
            }
        }
    }
}
