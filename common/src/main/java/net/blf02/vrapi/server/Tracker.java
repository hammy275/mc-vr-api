package net.blf02.vrapi.server;

import net.blf02.vrapi.data.VRPlayer;

import java.util.HashMap;
import java.util.HashSet;

public class Tracker {

    // Keys are the player's username
    public static final HashMap<String, VRPlayer> playerToVR = new HashMap<>();
    public static final HashSet<String> playersInVR = new HashSet<>();
}
