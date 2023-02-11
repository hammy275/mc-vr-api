package net.blf02.vrapi.client;

public class ReflectionConstants {
    // String constants
    public static final String VIVECRAFT_PACKAGE = "org.vivecraft";
    public static final String VIVECRAFT_MATH_PACKAGE = VIVECRAFT_PACKAGE + ".utils.math";
    public static final String VIVECRAFT_PROVIDER_PACKAGE = VIVECRAFT_PACKAGE + ".provider";
    public static final String VIVECRAFT_GAMEPLAY_PACKAGE = VIVECRAFT_PACKAGE + ".gameplay";
    // Reflected class references
    public static Class<?> VRPlayerRaw = null;
    public static Class<?> VRDataRaw = null;
    public static Class<?> VRDevicePoseRaw = null;
    public static Class<?> MCVR = null; // Note that this class is abstract
    public static Class<?> Matrix4f = null;
    public static Class<?> ControllerType = null; // Enum
    public static Class<?> VRSettings = null;
    public static Object[] ControllerType_ENUMS = null;
    // Whether Vivecraft is successfully loaded by the library.
    private static boolean hasVivecraft = false;

    public static void init() {
        try {
            VRPlayerRaw = Class.forName(VIVECRAFT_GAMEPLAY_PACKAGE + ".VRPlayer");
            VRDataRaw = Class.forName(VIVECRAFT_PACKAGE + ".api.VRData");
            VRDevicePoseRaw = VRDataRaw.getDeclaredClasses()[0];
            MCVR = Class.forName(VIVECRAFT_PROVIDER_PACKAGE + ".MCVR");
            ControllerType = Class.forName(VIVECRAFT_PROVIDER_PACKAGE + ".ControllerType");
            ControllerType_ENUMS = ControllerType.getEnumConstants();
            Matrix4f = Class.forName(VIVECRAFT_MATH_PACKAGE + ".Matrix4f");
            VRSettings = Class.forName(VIVECRAFT_PACKAGE + ".settings.VRSettings");
            hasVivecraft = true;
        } catch (ClassNotFoundException ignored) {}
    }

    public static boolean clientHasVivecraft() {
        return hasVivecraft;
    }
}
