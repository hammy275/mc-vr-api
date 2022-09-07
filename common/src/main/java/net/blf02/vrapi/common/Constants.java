package net.blf02.vrapi.common;

public class Constants {

    // Version {major, minor, patch}
    public static final int[] version = new int[]{2, 0, 0};

    // Debugging
    public static final boolean doDebugging = false;

    public static String getVersion() {
        return version[0] + "." + version[1] + "." + version[2];
    }

    public static String getNetworkVersion() {
        return version[0] + "." + version[1] + ".x";
    }


}
