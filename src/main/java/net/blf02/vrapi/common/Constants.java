package net.blf02.vrapi.common;

public class Constants {

    // Version {major, minor, patch}
    public static final int[] version = new int[]{1, 2, 0};

    // Debugging
    public static final boolean doDebugging = false;

    // String constants
    public static final String VIVECRAFT_PACKAGE = "org.vivecraft";
    public static final String VIVECRAFT_GAMEPLAY_PACKAGE = VIVECRAFT_PACKAGE + ".gameplay";
    public static final String VIVECRAFT_PROVIDER_PACKAGE = VIVECRAFT_PACKAGE + ".provider";

    // Reflected class references
    public static Class<?> VRPlayerRaw = null;
    public static Class<?> VRDataRaw = null;
    public static Class<?> VRDevicePoseRaw = null;
    public static Class<?> MCVR = null; // Note that this class is abstract

    public static Class<?> ControllerType = null; // Enum
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
            hasVivecraft = true;
        } catch (ClassNotFoundException ignored) {}
    }

    public static String getVersion() {
        return version[0] + "." + version[1] + "." + version[2];
    }

    public static String getNetworkVersion() {
        return version[0] + "." + version[1] + ".x";
    }

    public static boolean clientHasVivecraft() {
        return hasVivecraft;
    }


}
