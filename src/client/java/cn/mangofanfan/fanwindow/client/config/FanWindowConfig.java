package cn.mangofanfan.fanwindow.client.config;


/**
 * 本模组的配置类。
 * 应当通过ConfigManager.config访问以下配置。
 * ConfigManager使用单例模式与及时的配置文件读写避免出现错误，因此不应直接访问此类。
 */
public class FanWindowConfig {
    private boolean useNewTitleScreen = true;
    private boolean useNewBackgroundGlobally = false;
    private boolean useNewBackgroundInNewScreen = true;
    private boolean useNewCreateWorldScreen = true;

    private CustomPictureMode customPictureMode = CustomPictureMode.Disabled;

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

    public void setBgPicture(BgPicture bgPicture) {
        this.bgPicture = bgPicture;
    }

    public boolean isUseNewBackgroundGlobally() {
        return useNewBackgroundGlobally;
    }

    public void setUseNewBackgroundGlobally(boolean useNewBackgroundGlobally) {
        this.useNewBackgroundGlobally = useNewBackgroundGlobally;
    }

    public boolean isUseNewBackgroundInNewScreen() {
        return useNewBackgroundInNewScreen;
    }

    public void setUseNewBackgroundInNewScreen(boolean useNewBackgroundInNewScreen) {
        this.useNewBackgroundInNewScreen = useNewBackgroundInNewScreen;
    }

    public boolean isUseNewCreateWorldScreen() {
        return useNewCreateWorldScreen;
    }

    public void setUseNewCreateWorldScreen(boolean useNewCreateWorldScreen) {
        this.useNewCreateWorldScreen = useNewCreateWorldScreen;
    }

    public CustomPictureMode getCustomPictureMod() {
        return customPictureMode;
    }

    public void setCustomPictureMod(CustomPictureMode customPictureMode) {
        this.customPictureMode = customPictureMode;
    }
}
