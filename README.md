# MC VR API

## What?

This mod serves to act as an API for Vivecraft, to allow mod developers to interface with VR-specific features.

## How?

### Setup

#### Forge Setup:

Add the following to your `build.gradle`:

```
repositories {
    maven {
        name = "blf02"
        url = "https://blf02.net:4567"
    }
}
```

Add the following to the dependencies section of your `build.gradle`:

```
compileOnly fg.deobf("net.blf02:vrapi:VERSION")
runtimeOnly fg.deobf("net.blf02:vrapi:VERSION")
```
Where `VERSION` is the version you want to use (such as `1.1.0`). You can view a list of all available versions [here](https://github.com/hammy3502/mc-vr-api/wiki/Versions).

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

#### Fabric/Quilt:

Add the following to your `build.gradle`:

```
repositories {
    maven {
        name = "blf02"
        url = "https://blf02.net:4567"
    }
}
```

Then add the following to the `dependencies` section of your `build.gradle`:

```
modApi "net.blf02:vrapi:VERSION-fabric"
```
where `VERSION` is some version you want (such as `2.0.0-pre1`). Subsitute `fabric` for `quilt` if you want the Quilt version of `mc-vr-api`.

From there, add the following:
```java
List<EntrypointContainer<IVRAPI>> apis = FabricLoader.getInstance().getEntrypointContainers("vrapi", IVRAPI.class);
if (apis.size() > 0) {
    IVRAPI apiInstance = apis.get(0).getEntrypoint();
}
```
Store the `apiInstance` somewhere that your mod can access it, and you'll be good to go!

### Documentation

A good starting point is the wiki for `mc-vr-api`, which can be found [here!](https://github.com/hammy3502/mc-vr-api/wiki)

Documentation for everything in this mod can be found in docstrings. Feel free to look around the `api` package [here](https://github.com/hammy3502/mc-vr-api/tree/master/src/main/java/net/blf02/vrapi/api).

Of note are three important interfaces:

`IVRAPI`: An object that allows usage of the VR API. All "top-level" API functions are found here

`IVRPlayer`: An object that represents a player in VR, and all the objects used to track them.

`IVRData`: An object that represents the data for a given tracked object (the HMD or a controller).

## Why?

To make my own mods (and hopefully other people's mods) work better in the context of VR, and to allow for the creation of VR-specific mods, by both others and me.

If you want an example of this, I've created a small "demo" mod that shows off the kinds of things this API can do. It's called [mc-vr-playground](https://github.com/hammy3502/mc-vr-playground), which you can download and throw into any installation!

## Some Questions and Answers

#### Q: What support is there for 1.17 and 1.18?

A: 1.18.2 is supported! Due to Vivecraft never supporting 1.17.x under mod loaders, neither will this.

#### Q: Which Minecraft versions will this mod release for?

A: This mod will release for every Minecraft version up to and including the first version with the official Vivecraft API. All versions that are supported at the time will receive a "3.0" update that reflects the Vivecraft API. 

If the API never releases for some reason, I hope to continue to release this mod under newer versions as long as possible.

#### Q: Which Minecraft versions will continue to receive updates/be supported?

A: I plan to support the latest version of the two most recent major releases of Minecraft that have support for Vivecraft modded.

#### Q: Fabric?

A: Yes! With the release of the Vivecraft port to a more loader-less architecture, Fabric is supported! Quilt is also accounted for, but currently not tested, so use at your own risk!

#### Q: Why aren't there many updates? Why hasn't this updated in a while?

A: `mc-vr-api` is a relatively small program, and doesn't have many features to implement. As a result, the mod doesn't get updated much due to not needing updates in the first place.

#### Q: My game crashed! I got a `java.lang.NoClassDefFoundError` exception!

A: This exception tends to happen when your mod references something VR-related when someone doesn't have this API mod installed. 

If your mod always requires VR, then odds are, you forgot to run this mod alongside the mod you're testing

If your mod doesn't always require VR, then your code is attempting to reference something VR-related, even though the VR API isn't there (this is why the `VRPlugin` and `VRPluginStatus` classes above are separated).

#### Q: When can I start using the `IVRAPI` instance?

A: `IVRAPI` instances are handed out during the `FMLCommonSetupEvent` event in Forge environments and during mod initialization in Fabric/Quilt environments.

