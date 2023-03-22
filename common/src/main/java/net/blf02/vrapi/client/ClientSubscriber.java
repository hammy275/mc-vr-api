package net.blf02.vrapi.client;

import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.blf02.vrapi.VRAPIMod;
import net.blf02.vrapi.VRAPIModClient;
import net.blf02.vrapi.common.network.Network;
import net.blf02.vrapi.common.network.packets.VRDataPacket;
import net.blf02.vrapi.common.network.packets.VersionSyncPacket;
import net.blf02.vrapi.data.VRData;
import net.blf02.vrapi.data.VRPlayer;
import net.minecraft.network.chat.Component;
import net.blf02.vrapi.debug.DevModeData;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class ClientSubscriber {
    public static boolean didJoinPacket = false;

    public static void onPlayerTick(Player player) {
        if (player.level.isClientSide) {
            if (!didJoinPacket) {
                onLogin(player);
                didJoinPacket = true;
            }

            VRPlayer vrPlayer = VRDataGrabber.getVRPlayer(VRDataGrabber.PlayerType.WORLD_POST);

            // Let dev set pos here
            if (VRAPIMod.USE_DEV_FEATURES) {
                if (VRAPIModClient.POSITION_LEFT.isDown()) {
                    DevModeData.leftPos = player.getEyePosition().add(player.getLookAngle().scale(2));
                    DevModeData.leftRot = Vec3.atLowerCornerOf(player.getDirection().getNormal());
                }

                if (VRAPIModClient.POSITION_RIGHT.isDown()) {
                    DevModeData.rightPos = player.getEyePosition().add(player.getLookAngle().scale(2));
                    DevModeData.rightRot = Vec3.atLowerCornerOf(player.getDirection().getNormal());
                }

                if (VRAPIModClient.POSITION_HMD.isDown()) {
                    DevModeData.hmdPos = player.getEyePosition().add(player.getLookAngle().scale(2));
                    DevModeData.hmdRot = Vec3.atLowerCornerOf(player.getDirection().getNormal());
                }

                VRData hmdData = new VRData(DevModeData.hmdPos, DevModeData.hmdRot, 0, new Matrix4f());
                VRData leftData = new VRData(DevModeData.leftPos, DevModeData.leftRot, 0, new Matrix4f());
                VRData rightData = new VRData(DevModeData.rightPos, DevModeData.rightRot, 0, new Matrix4f());
                vrPlayer = new VRPlayer(hmdData, rightData, leftData, hmdData, hmdData);
                DevModeData.fakePlayer = vrPlayer;

                player.level.addParticle(new DustParticleOptions(new Vector3f(1, 0, 0), 1),
                        DevModeData.leftPos.x(), DevModeData.leftPos.y(), DevModeData.leftPos.z(),
                        0.01, 0.01, 0.01);
                player.level.addParticle(new DustParticleOptions(new Vector3f(0, 0, 1), 1),
                        DevModeData.rightPos.x(), DevModeData.rightPos.y(), DevModeData.rightPos.z(),
                        0.01, 0.01, 0.01);
                player.level.addParticle(new DustParticleOptions(new Vector3f(1, 1, 1), 1),
                        DevModeData.hmdPos.x(), DevModeData.hmdPos.y(), DevModeData.hmdPos.z(),
                        0.01, 0.01, 0.01);
            }

            if (vrPlayer != null) {
                boolean seated = VRAPIMod.USE_DEV_FEATURES ? false : VRDataGrabber.isSeated();
                boolean leftHanded = VRAPIMod.USE_DEV_FEATURES ? false : VRDataGrabber.isLeftHanded();
                Network.CHANNEL.sendToServer(new VRDataPacket(vrPlayer,
                        seated, leftHanded));
            }
            if (ServerHasAPI.countForAPIResponse) {
                if (--ServerHasAPI.apiResponseCountdown < 1) {
                    ServerHasAPI.countForAPIResponse = false;
                    player.sendSystemMessage(
                            Component.translatable("message.vrapi.no_api_server"));
                }
            }
        }
    }

    private static void onLogin(Player player) {
        didJoinPacket = false;
        ServerHasAPI.serverHasAPI = false;
        ServerHasAPI.countForAPIResponse = true;
        ServerHasAPI.apiResponseCountdown = 100;
        Network.CHANNEL.sendToServer(new VersionSyncPacket(Network.PROTOCOL_VERSION));
    }
}
