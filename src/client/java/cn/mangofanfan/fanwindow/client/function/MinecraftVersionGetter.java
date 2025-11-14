package cn.mangofanfan.fanwindow.client.function;

import net.fabricmc.loader.api.FabricLoader;

import java.util.Optional;

public class MinecraftVersionGetter {
    public static String getMinecraftVersion() {
        Optional<String> version = FabricLoader.getInstance()
                .getModContainer("minecraft")
                .map(modContainer -> modContainer.getMetadata().getVersion().getFriendlyString());
        return version.orElse("Unknown");
    }
}
