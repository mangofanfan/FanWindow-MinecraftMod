package cn.mangofanfan.fanwindow.client.function;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;

public class NativeResourceLoaderImpl extends NativeResourceLoader {
    @Override
    NativeImageBackedTexture getNativeImageBackedTexture(String pathString, NativeImage nativeImage) {
        return new NativeImageBackedTexture(() -> pathString, nativeImage);
    }
}
