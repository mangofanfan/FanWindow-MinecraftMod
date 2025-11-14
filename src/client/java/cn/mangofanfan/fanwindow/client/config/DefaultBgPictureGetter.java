package cn.mangofanfan.fanwindow.client.config;

import cn.mangofanfan.fanwindow.client.function.MinecraftVersionGetter;
import net.fabricmc.loader.api.FabricLoader;

public class DefaultBgPictureGetter {
    /**
     * 根据运行的 Minecraft 版本获取对应主题的官方艺术图片作为背景。
     * 默认回退图片为最新版本的艺术图片，即 铜器时代。
     * @return BgPicture
     */
    public static BgPicture getDefaultBgPicture() {
        return switch (MinecraftVersionGetter.getMinecraftVersion()) {
            case "1.21", "1.21.1" -> BgPicture.Tricky_Trials_Artwork_png;
            case "1.21.2", "1.21.3" -> BgPicture.Bundles_of_Bravery_Artwork_png;
            case "1.21.4" -> BgPicture.The_Garden_Awakens_Artwork_png;
            case "1.21.5" -> BgPicture.Spring_to_Life_Artwork_png;
            default -> BgPicture.The_Copper_Age_Key_Art_png;
        };
    }
}
