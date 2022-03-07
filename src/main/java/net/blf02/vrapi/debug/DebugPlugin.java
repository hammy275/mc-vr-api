package net.blf02.vrapi.debug;

import net.blf02.vrapi.api.IVRAPI;
import net.blf02.vrapi.api.VRAPIPlugin;
import net.blf02.vrapi.api.VRAPIPluginProvider;
import net.blf02.vrapi.common.Constants;

@VRAPIPlugin
public class DebugPlugin implements VRAPIPluginProvider {

    public static IVRAPI vrAPI = null;

    @Override
    public void getVRAPI(IVRAPI api) {
        vrAPI = api;
        if (Constants.doDebugging) {
            vrAPI.registerVRPlayerTickHandler(DebugSubscriber::onVRTick);
        }
    }

    public boolean isPluginLoaded() {
        return vrAPI != null;
    }
}
