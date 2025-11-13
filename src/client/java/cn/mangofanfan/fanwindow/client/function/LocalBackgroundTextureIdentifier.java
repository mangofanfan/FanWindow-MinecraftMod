package cn.mangofanfan.fanwindow.client.function;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalBackgroundTextureIdentifier {
    Identifier identifier;
    int[] textureSize;
    Logger logger = LoggerFactory.getLogger(LocalBackgroundTextureIdentifier.class);
    SimpleToastBuilder toastBuilder =  new SimpleToastBuilder();

    public LocalBackgroundTextureIdentifier(Path filePath) {
        try {
            String path = "dynamic/textures/custom.png";
            NativeImage nativeImage = NativeImage.read(Files.newInputStream(filePath));
            identifier = Identifier.of("fanwindow", path);
            textureSize = new int[]{nativeImage.getWidth(), nativeImage.getHeight()};

            NativeImageBackedTexture texture = new NativeResourceLoaderImpl().getNativeImageBackedTexture(path, nativeImage);
            MinecraftClient.getInstance().getTextureManager().registerTexture(identifier, texture);
            logger.info("Successfully loaded Background Texture in Config Dir at {}", filePath);
        }
        catch (IOException e) {
            // 未找到图片的错误处理
            logger.warn("Error loading Local Background Texture in Config Dir at {}", filePath);
            logger.warn("If your Custom Background Picture Mode is ConfigDir, you will get a default image as background.");
            toastBuilder.show(Text.translatable("gui.fanwindow.local_identifier_not_found.title"), Text.translatable("gui.fanwindow.local_identifier_not_found.description"));
            identifier = Identifier.of("fanwindow", "textures/artwork/custom.png");
            textureSize = new int[]{1600, 900};
        }
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public int[] getTextureSize() {
        return  textureSize;
    }
}
