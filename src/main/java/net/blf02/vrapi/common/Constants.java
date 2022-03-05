package net.blf02.vrapi.common;

public class Constants {

    // Debugging
    public static final boolean doDebugging = true;

    // String constants
    public static final String VIVECRAFT_PACKAGE = "org.vivecraft";
    public static final String VIVECRAFT_GAMEPLAY_PACKAGE = VIVECRAFT_PACKAGE + ".gameplay";

    // Reflected class references
    public static Class<?> VRPlayerRaw = null;
    public static Class<?> VRDataRaw = null;
    public static Class<?> VRDevicePoseRaw = null;

    // Whether Vivecraft is successfully loaded by the library.
    private static boolean hasVivecraft = false;

    public static void init() {
        try {
            VRPlayerRaw = Class.forName(VIVECRAFT_GAMEPLAY_PACKAGE + ".VRPlayer");
            VRDataRaw = Class.forName(VIVECRAFT_PACKAGE + ".api.VRData");
            VRDevicePoseRaw = VRDataRaw.getDeclaredClasses()[0];
            hasVivecraft = true;
        } catch (ClassNotFoundException ignored) {}
    }

    public static boolean clientHasVivecraft() {
        return hasVivecraft;
    }

}
