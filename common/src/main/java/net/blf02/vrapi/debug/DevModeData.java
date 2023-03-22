package net.blf02.vrapi.debug;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.blf02.vrapi.data.VRData;
import net.blf02.vrapi.data.VRPlayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class DevModeData {

    public static Vec3 leftPos = Vec3.ZERO;
    public static Vec3 rightPos = Vec3.ZERO;
    public static Vec3 hmdPos = Vec3.ZERO;

    public static Vec3 leftRot = Vec3.ZERO;
    public static Vec3 rightRot = Vec3.ZERO;
    public static Vec3 hmdRot = Vec3.ZERO;

    public static IVRPlayer fakePlayer = new VRPlayer(
            new VRData(Vec3.ZERO, Vec3.ZERO, 0, new Matrix4f()),
            new VRData(Vec3.ZERO, Vec3.ZERO, 0, new Matrix4f()),
            new VRData(Vec3.ZERO, Vec3.ZERO, 0, new Matrix4f()),
            new VRData(Vec3.ZERO, Vec3.ZERO, 0, new Matrix4f()),
            new VRData(Vec3.ZERO, Vec3.ZERO, 0, new Matrix4f())
    );

}
