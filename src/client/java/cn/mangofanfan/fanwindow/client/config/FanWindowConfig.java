package cn.mangofanfan.fanwindow.client.config;


/**
 * 本模组的配置类。
 * 应当通过ConfigManager.config访问以下配置。
 * ConfigManager使用单例模式与及时的配置文件读写避免出现错误，因此不应直接访问此类。
 */
public class FanWindowConfig {
    private boolean useNewTitleScreen = true;
    private BgPicture bgPicture = BgPicture.Spring_to_Life_Artwork_png;

    public boolean isUseNewTitleScreen() {
        return useNewTitleScreen;
    }

    public void setUseNewTitleScreen(boolean useNewTitleScreen) {
        this.useNewTitleScreen = useNewTitleScreen;
    }

    public BgPicture getBgPicture() {
        return bgPicture;
    }

    public void setBgPicture(String bgPictureName) {
        this.bgPicture = BgPicture.valueOf(bgPictureName.replace(".png", "_png"));
    }
}
