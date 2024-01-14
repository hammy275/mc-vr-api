package net.blf02.neoforge;

import net.blf02.vrapi.api.IVRAPI;

public interface VRAPIPluginProvider {

    /**
     * Retrieves the VR API for use in your mod. You can store the API object somewhere, then use
     * it at any time to interact with the API.
     *
     * If this method is not called, then the place your mod is running does not have access to the API!
     *
     * @param api The API object containing all API methods.
     */
    public void getVRAPI(IVRAPI api);
}
