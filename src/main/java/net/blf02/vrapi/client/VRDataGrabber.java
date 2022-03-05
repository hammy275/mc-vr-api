package net.blf02.vrapi.client;

import net.blf02.vrapi.VRAPIMod;
import net.blf02.vrapi.api.data.VRData;
import net.blf02.vrapi.api.data.VRPlayer;
import net.blf02.vrapi.common.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VRDataGrabber {

    // VRPlayer from Vivecraft
    protected static Method VRPlayer_GET;
    protected static Field VRPlayer_vrdata_world_post;

    // VRData from Vivecraft
    protected static Field VRData_hmd;
    protected static Field VRData_h0;
    protected static Field VRData_h1;

    // VRDevicePose from Vivecraft
    protected static Method VRDevicePose_getPosition;
    protected static Method VRDevicePose_getDirection;

    public static void init() {
        if (!Constants.clientHasVivecraft()) {
            VRAPIMod.LOGGER.log(Level.INFO, "Vivecraft was not detected! Not reflecting...");
        } else {
            VRPlayer_GET = getMethod(Constants.VRPlayerRaw, "get");
            VRPlayer_vrdata_world_post = getField(Constants.VRPlayerRaw, "vrdata_world_post");

            VRData_hmd = getField(Constants.VRDataRaw, "hmd");
            VRData_h0 = getField(Constants.VRDataRaw, "h0");
            VRData_h1 = getField(Constants.VRDataRaw, "h1");

            VRDevicePose_getPosition = getMethod(Constants.VRDevicePoseRaw, "getPosition");
            VRDevicePose_getDirection = getMethod(Constants.VRDevicePoseRaw, "getDirection");
        }
    }

    public static VRPlayer getVRPlayer() throws InvocationTargetException, IllegalAccessException {
        if (!Constants.clientHasVivecraft()) {
            return null; // TODO: Replace with a "not-in-vr" VRPlayer (add a field to denote that player is in vr)
        }

        Object vrPlayerRaw = VRPlayer_GET.invoke(null); // Get our "VRPlayer" from Vivecraft
        Object vrDataRaw = VRPlayer_vrdata_world_post.get(vrPlayerRaw); // Get the "VRData" from Vivecraft

        Object hmdDevicePoseRaw = VRData_hmd.get(vrDataRaw); // Get the VRDevicePose for the HMD
        Object c0DevicePoseRaw = VRData_h0.get(vrDataRaw);
        Object c1DevicePoseRaw = VRData_h1.get(vrDataRaw);

        Vector3d hmdPosition = (Vector3d) VRDevicePose_getPosition.invoke(hmdDevicePoseRaw); // Gets the position for the HMD in the world.
        Vector3d hmdLookVec = (Vector3d) VRDevicePose_getDirection.invoke(hmdDevicePoseRaw);

        Vector3d c0Position = (Vector3d) VRDevicePose_getPosition.invoke(c0DevicePoseRaw);
        Vector3d c0LookVec = (Vector3d) VRDevicePose_getDirection.invoke(c0DevicePoseRaw);

        Vector3d c1Position = (Vector3d) VRDevicePose_getPosition.invoke(c1DevicePoseRaw);
        Vector3d c1LookVec = (Vector3d) VRDevicePose_getDirection.invoke(c1DevicePoseRaw);

        return new VRPlayer(new VRData(hmdPosition, hmdLookVec),
                new VRData(c0Position, c0LookVec), new VRData(c1Position, c1LookVec),
                Minecraft.getInstance().player);
    }

    public static Field getField(Class<?> clazz, String field) {
        try {
            return clazz.getField(field);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not get field " + field + " from " + clazz);
        }
    }

    public static Method getMethod(Class<?> clazz, String method) {
        try {
            return clazz.getMethod(method);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Could not load method " + method + " from " + clazz);
        }
    }

}
