package net.blf02.vrapi.client;

import net.blf02.vrapi.VRAPIMod;
import net.blf02.vrapi.common.Constants;
import net.blf02.vrapi.data.VRData;
import net.blf02.vrapi.data.VRPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VRDataGrabber {

    // vr field in net.minecraft.client.Minecraft
    public static Field Minecraft_vr;  // Type MCVR
    public static Object Minecraft_vr_Instance; // Type MCVR

    // VRPlayer from Vivecraft
    protected static Method VRPlayer_GET; // Returns VRPlayer
    protected static Field VRPlayer_vrdata_world_post; // Type VRData

    // VRData from Vivecraft
    protected static Field VRData_hmd; // Type VRDevicePose
    protected static Field VRData_c0; // Type VRDevicePose
    protected static Field VRData_c1; // Type VRDevicePose
    protected static Field VRData_eye0; // Type VRDevicePose (corresponds to left eye)
    protected static Field VRData_eye1; // Type VRDevicePose (corresponds to right eye)

    // VRDevicePose from Vivecraft
    protected static Method VRDevicePose_getPosition; // Returns Vector3d (vanilla type)
    protected static Method VRDevicePose_getDirection; // Returns Vector3d (vanilla type)
    protected static Method VRDevicePose_getRoll; // Returns float

    // MCVR from Vivecraft
    public static Method MCVR_triggerHapticPulse; // Returns void

    public static void init() {
        if (!Constants.clientHasVivecraft()) {
            VRAPIMod.LOGGER.log(Level.INFO, "Vivecraft was not detected! Not reflecting...");
        } else {
            try {
                VRPlayer_GET = getMethod(Constants.VRPlayerRaw, "get");
                VRPlayer_vrdata_world_post = getField(Constants.VRPlayerRaw, "vrdata_world_post");

                VRData_hmd = getField(Constants.VRDataRaw, "hmd");
                VRData_c0 = getField(Constants.VRDataRaw, "c0");
                VRData_c1 = getField(Constants.VRDataRaw, "c1");
                VRData_eye0 = getField(Constants.VRDataRaw, "eye0");
                VRData_eye1 = getField(Constants.VRDataRaw, "eye1");

                VRDevicePose_getPosition = getMethod(Constants.VRDevicePoseRaw, "getPosition");
                VRDevicePose_getDirection = getMethod(Constants.VRDevicePoseRaw, "getDirection");
                VRDevicePose_getRoll = getMethod(Constants.VRDevicePoseRaw, "getRoll");

                Minecraft_vr = getField(Minecraft.class, "vr");
                Minecraft_vr_Instance = Minecraft_vr.get(Minecraft.getInstance());

                MCVR_triggerHapticPulse = getMethod(Constants.MCVR, "triggerHapticPulse",
                        Constants.ControllerType, float.class, float.class, float.class, float.class);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Fatal error! Could not get an important object! Please report this!");
            }

        }
    }

    public static VRPlayer getVRPlayer() {
        if (!Constants.clientHasVivecraft()) {
            return null;
        }

        try {
            Object vrPlayerRaw = VRPlayer_GET.invoke(null); // Get our "VRPlayer" from Vivecraft
            Object vrDataRaw = VRPlayer_vrdata_world_post.get(vrPlayerRaw); // Get the "VRData" from Vivecraft

            Object hmdDevicePoseRaw = VRData_hmd.get(vrDataRaw); // Get the VRDevicePose for the HMD
            Object c0DevicePoseRaw = VRData_c0.get(vrDataRaw);
            Object c1DevicePoseRaw = VRData_c1.get(vrDataRaw);
            Object eye0DevicePoseRaw = VRData_eye0.get(vrDataRaw);
            Object eye1DevicePoseRaw = VRData_eye1.get(vrDataRaw);

            Vector3d hmdPosition = (Vector3d) VRDevicePose_getPosition.invoke(hmdDevicePoseRaw); // Gets the position for the HMD in the world.
            Vector3d hmdLookVec = (Vector3d) VRDevicePose_getDirection.invoke(hmdDevicePoseRaw);
            float hmdRoll = (float) VRDevicePose_getRoll.invoke(hmdDevicePoseRaw);

            Vector3d c0Position = (Vector3d) VRDevicePose_getPosition.invoke(c0DevicePoseRaw);
            Vector3d c0LookVec = (Vector3d) VRDevicePose_getDirection.invoke(c0DevicePoseRaw);
            float c0roll = (float) VRDevicePose_getRoll.invoke(c0DevicePoseRaw);

            Vector3d c1Position = (Vector3d) VRDevicePose_getPosition.invoke(c1DevicePoseRaw);
            Vector3d c1LookVec = (Vector3d) VRDevicePose_getDirection.invoke(c1DevicePoseRaw);
            float c1roll = (float) VRDevicePose_getRoll.invoke(c1DevicePoseRaw);

            Vector3d eye0Position = (Vector3d) VRDevicePose_getPosition.invoke(eye0DevicePoseRaw);
            Vector3d eye0LookVec = (Vector3d) VRDevicePose_getDirection.invoke(eye0DevicePoseRaw);
            float eye0roll = (float) VRDevicePose_getRoll.invoke(eye0DevicePoseRaw);

            Vector3d eye1Position = (Vector3d) VRDevicePose_getPosition.invoke(eye1DevicePoseRaw);
            Vector3d eye1LookVec = (Vector3d) VRDevicePose_getDirection.invoke(eye1DevicePoseRaw);
            float eye1roll = (float) VRDevicePose_getRoll.invoke(eye1DevicePoseRaw);

            return new VRPlayer(new VRData(hmdPosition, hmdLookVec, hmdRoll),
                    new VRData(c0Position, c0LookVec, c0roll), new VRData(c1Position, c1LookVec, c1roll),
                    new VRData(eye0Position, eye0LookVec, eye0roll), new VRData(eye1Position, eye1LookVec, eye1roll));
        }  catch (InvocationTargetException | IllegalAccessException e) {
            // We shouldn't error here, as we know these fields and methods exist due to getField() and getMethod()
            // combined with having access to Vivecraft's codebase on GitHub
            throw new RuntimeException("Could not obtain data from Vivecraft! Something has gone horribly wrong.");
        }
    }

    public static boolean inVR() {
        if (!Constants.clientHasVivecraft()) {
            return false;
        }
        try {
            Object vrPlayerRaw = VRPlayer_GET.invoke(null); // Try to get vrPlayer from Vivecraft
            return true; // If we got the above, we're definitely in VR.
        } catch (InvocationTargetException | IllegalAccessException e) {
            return false; // If we failed to grab the above, we definitely are NOT in VR.
        }
    }

    public static Field getField(Class<?> clazz, String field) {
        try {
            return clazz.getField(field);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not get field " + field + " from " + clazz);
        }
    }

    public static Method getMethod(Class<?> clazz, String method, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(method, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Could not load method " + method + " from " + clazz);
        }
    }

}
