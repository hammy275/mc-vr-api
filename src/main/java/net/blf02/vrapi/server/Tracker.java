package net.blf02.vrapi.server;

import net.blf02.vrapi.data.VRPlayer;

import java.util.HashMap;

public class Tracker {

    // Keys are the player's username
    public static final HashMap<String, VRPlayer> playerToVR = new HashMap<>();
}
