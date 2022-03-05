package net.blf02.vrapi.common;

import net.blf02.vrapi.api.VRAPIPlugin;
import net.blf02.vrapi.api.VRAPIPluginProvider;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class APIProviderInit {

    // Huge thanks to JEITweaker (released under MIT license) for basically all the code below
    // https://github.com/CraftTweaker/JEITweaker/blob/1.16/src/main/java/com/blamejared/jeitweaker/implementation/JeiTweakerInitializer.java

    public static void init() {
        findPlugins().forEach(provider -> {
            provider.getVRAPI(VRAPI.VRAPIInstance);
        });

    }

    protected static List<VRAPIPluginProvider> findPlugins() {
        return ModList.get().getAllScanData().stream().flatMap(APIProviderInit::findValidAnnotations)
                .map(APIProviderInit::initPlugin).filter(Objects::nonNull).collect(Collectors.toList());
    }

    protected static Stream<Type> findValidAnnotations(final ModFileScanData data) {
        final Type annotationType = Type.getType(VRAPIPlugin.class);
        return data.getAnnotations().stream().filter(it -> annotationType.equals(it.getAnnotationType()))
                .map(ModFileScanData.AnnotationData::getClassType);
    }

    protected static VRAPIPluginProvider initPlugin(final Type type) {
        try {
            final Class<?> clazz = Class.forName(type.getClassName(), false, APIProviderInit.class.getClassLoader());
            if (!VRAPIPluginProvider.class.isAssignableFrom(clazz)) {
                throw new ClassCastException(clazz.getName() + " does not implement VRAPIPluginProvider!");
            }
            final Constructor<? extends VRAPIPluginProvider> constructor = ((Class<? extends VRAPIPluginProvider>) clazz).getConstructor();
            return constructor.newInstance();
        } catch (final ReflectiveOperationException | ClassCastException e) {
            System.out.println("Could not initialize plugin " + type.getClassName());
            return null;
        }
    }

}
