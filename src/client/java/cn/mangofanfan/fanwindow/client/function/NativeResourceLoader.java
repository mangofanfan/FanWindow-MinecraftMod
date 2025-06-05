package cn.mangofanfan.fanwindow.client.function;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;

public abstract class NativeResourceLoader {
     abstract NativeImageBackedTexture getNativeImageBackedTexture(String pathString, NativeImage nativeImage);
}
