package cn.mangofanfan.fanwindow.client.config;

import java.util.ArrayList;


/**
 * 模组配置中，背景图片的枚举类，包含模组提供的背景图片。
 * Minecraft要求图片资源必须为png格式，因此枚举项均以_png结尾。
 */
public enum BgPicture {
    /**
     * 勇气之袋的官方艺术作品 1.21.2
     */
    Bundles_of_Bravery_Artwork_png("Bundles_of_Bravery_Artwork.png"),
    /**
     * 春意盎然的官方艺术作品 1.21.5
     */
    Spring_to_Life_Artwork_png("Spring_to_Life_Artwork.png"),
    /**
     * 苍园觉醒的官方艺术作品 1.21.4
     */
    The_Garden_Awakens_Artwork_png("The_Garden_Awakens_Artwork.png"),
    /**
     * 棘巧试炼的官方艺术作品 1.21
     */
    Tricky_Trials_Artwork_png("Tricky_Trials_Artwork.png"),
    /**
     * 铜器时代 1.21.9
     */
    The_Copper_Age_Key_Art_png("The_Copper_Age_Key_Art.png"),

    /**
     * 末影龙
     */
    Ender_Dragon_png("Ender_Dragon.png");


    private final String picName;
    BgPicture(String image) {
        this.picName = image;
    }

    /**
     * 获取文件名组成的ArrayList，文件名通过getPicName获取，格式后缀为.png
     * 由于已经替换实现方式，已弃用。
     * @return ArrayList<String>，由全部图片名称组成。
     */
    @Deprecated
    public static Iterable<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        for (BgPicture bgPicture : BgPicture.values()) {
            values.add(bgPicture.getPicName());
        }
        return values;
    }

    /**
     * 获取图片的全名，格式后缀还原为.png
     * @return String，文件名
     */
    public String getPicName() {
        return this.picName;
    }

    /**
     * 返回图片的资源路径
     * @return String，用于Identifier.of("fanwindow", ______);中
     */
    public String getPath() {
        return "textures/artwork/" + getPicName().toLowerCase();
    }

    /**
     * 返回图片的尺寸。
     * @return int[]，0 -> width，1 -> height。
     */
    public int[] getPicSize() {
        int[] size = {0, 0};
        switch (this) {
            case Bundles_of_Bravery_Artwork_png:
                size[0] = 889;
                size[1] = 500;
                break;
            case Spring_to_Life_Artwork_png:
                size[0] = 1920;
                size[1] = 1080;
                break;
            case The_Garden_Awakens_Artwork_png:
                size[0] = 1280;
                size[1] = 720;
                break;
            case Tricky_Trials_Artwork_png:
                size[0] = 2560;
                size[1] = 1440;
                break;
            case Ender_Dragon_png:
                size[0] = 1600;
                size[1] = 900;
                break;
            case The_Copper_Age_Key_Art_png:
                size[0] = 1920;
                size[1] = 1350;
                break;
        }
        return size;
    }
}
