# VR API

## What?

This mod serves to act as an API for Vivecraft, to allow mod developers to interface with VR-specific features.

## How?

### Setup

Add the following to your `build.gradle`:

```
// Note: This uses HTTP, so don't use this on an untrusted network.
// There are plans to switch to https and find an actual provider soon(TM)
repositories {
    maven {
        name = "blf02"
        url = "http://blf02.net:4567"
        allowInsecureProtocol = true
    }
}
```

Add the following to the dependencies section of your `build.gradle`:

```
compileOnly fg.deobf("net.blf02:vrapi:VERSION")
runtimeOnly fg.deobf("net.blf02:vrapi:VERSION")
```
Where `VERSION` is the version you want to use (such as `1.0.0`)

After adding these to your `build.gradle`, exit and re-open your IDE. You may need to run `gradlew --refresh-dependencies` afterwards (or `./gradlew --refresh-dependencies` if you're on Linux).

Once done, make two classes. They can have any name you'd like, but in this, I'll refer to them as `VRPlugin` and `VRPluginStatus`. In `VRPlugin`, you should have this code:
```java
@VRAPIPlugin
public class VRPlugin implements VRAPIPluginProvider {

    public static IVRAPI vrAPI = null;

    @Override
    public void getVRAPI(IVRAPI ivrapi) {
        vrAPI = ivrapi;
        VRPluginStatus.hasPlugin = true;
    }
}
```
and in `VRPluginStatus` you should have this code:

```java
public class VRPluginStatus {
    public static boolean hasPlugin = false;
}
```

With this all done, you should be good to go! You can reference `VRPlugin.vrAPI`, and access the VR API from there!

Take a look at the Documentation section below to figure out what you can do.

### Documentation

A good starting point is the wiki for `mc-vr-api`, which can be found [here!](https://github.com/hammy3502/mc-vr-api/wiki)

Documentation for everything in this mod can be found in docstrings. Feel free to look around the `api` package [here](https://github.com/hammy3502/mc-vr-api/tree/master/src/main/java/net/blf02/vrapi/api).

Of note are three important interfaces:

`IVRAPI`: An object that allows usage of the VR API. All "top-level" API functions are found here

`IVRPlayer`: An object that represents a player in VR, and all the objects used to track them.

`IVRData`: An object that represents the data for a given tracked object (the HMD or a controller).

## Why?

To make my own mods (and hopefully other people's mods) work better in the context of VR, and to allow for the creation of VR-specific mods, by both others and me.

## Some Questions and Answers

#### Q: How about 1.17/1.18 and higher?

A: The developers of Vivecraft plan to create an official API. For that reason, I have no reason or need to port this up to 1.17/1.18. However, I do plan to continue to maintain this API until the modding scene generally switches away from 1.16 as a Minecraft version.

#### Q: Fabric?

A: No. Vivecraft is based on Forge, so this API is only for Forge. I have no plans to write this for MCXR, and MCXR plans to have an official API anyways.

#### Q: My game crashed! I got a `java.lang.NoClassDefFoundError` exception!

A: This exception tends to happen when your mod references something VR-related when someone doesn't have this API mod installed. 

If your mod always requires VR, then odds are, you forgot to run this mod alongside the mod you're testing

If your mod doesn't always require VR, then your code is attempting to reference something VR-related, even though the VR API isn't there (this is why the `VRPlugin` and `VRPluginStatus` classes above are separated).

#### Q: When can I start using the `IVRAPI` instance?

A: `IVRAPI` instances are handed out during the `FMLCommonSetupEvent` event.

