package cn.mangofanfan.fanwindow.client.config;

import java.util.ArrayList;

public enum BgPicture {
    /**
     * 勇气之袋的官方艺术作品 1.21.2
     */
    Bundles_of_Bravery_Artwork_png,
    /**
     * 春意盎然的官方艺术作品 1.21.5
     */
    Spring_to_Life_Artwork_png,
    /**
     * 苍园觉醒的官方艺术作品 1.21.4
     */
    The_Garden_Awakens_Artwork_png,
    /**
     * 棘巧试炼的官方艺术作品 1.21
     */
    Tricky_Trials_Artwork_png;

    public static Iterable<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        for (BgPicture bgPicture : BgPicture.values()) {
            values.add(bgPicture.getPicName());
        }
        return values;
    }

    public String getPicName() {
        return this.toString().replace("_png", ".png");
    }

    /**
     * 返回图片的资源路径
     * @return String，用于Identifier.of("fanwindow", ______);中
     */
    public String getPath() {
        return "textures/artwork/" + getPicName().toLowerCase();
    }
}
