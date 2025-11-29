package cn.mangofanfan.fanwindow.client.config;


/**
 * 本模组的配置类。
 * 应当通过ConfigManager.config访问以下配置。
 * ConfigManager使用单例模式与及时的配置文件读写避免出现错误，因此不应直接访问此类。
 */
public class FanWindowConfig {
    /**
     * 使用新标题屏幕，同时在新标题屏幕中提供切换到原版标题屏幕的按钮、在原版中提供切换到新的按钮。
     */
    private boolean useNewTitleScreen = true;

    /**
     * 禁用原版标题屏幕，即取消上面的切换按钮。
     */
    private boolean disableVanillaTitleScreen = false;

    /**
     * 全局使用新背景，即静态图片背景。
     */
    private boolean useNewBackgroundGlobally = true;

    /**
     * 在新版标题屏幕中使用新背景。在全局使用新背景启用时无效。
     */
    private boolean useNewBackgroundInNewScreen = true;

    /**
     * 使用新的创建世界屏幕。
     */
    private boolean useNewCreateWorldScreen = true;

    /**
     * 新背景图片模式。
     */
    private CustomPictureMode customPictureMode = CustomPictureMode.Disabled;

    /**
     * 新背景图片模式为 Disabled 时，在此选择内置的图片。
     */
    private BgPicture bgPicture = DefaultBgPictureGetter.getDefaultBgPicture();

    /**
     * 退出 Minecraft 客户端时需要二次确认。
     */
    private boolean exitMinecraftConfirm = true;

    /**
     * 退出世界时需要二次确认。
     */
    private boolean exitWorldConfirm = true;

    /**
     * （多人游戏）意外断开连接时，是否自动重新连接？
     */
    private boolean enableAutoReconnect = true;

    /**
     * （多人游戏）意外断开连接后，重新连接的间隔时间？
     */
    private int autoReconnectWaitTime = 5;

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

    public CustomPictureMode getCustomPictureMode() {
        return customPictureMode;
    }

    public void setCustomPictureMode(CustomPictureMode customPictureMode) {
        this.customPictureMode = customPictureMode;
    }

    public boolean isDisableVanillaTitleScreen() {
        return disableVanillaTitleScreen;
    }

    public void setDisableVanillaTitleScreen(boolean disableVanillaTitleScreen) {
        this.disableVanillaTitleScreen = disableVanillaTitleScreen;
    }

    public boolean isExitMinecraftConfirm() {
        return exitMinecraftConfirm;
    }

    public void setExitMinecraftConfirm(boolean exitMinecraftConfirm) {
        this.exitMinecraftConfirm = exitMinecraftConfirm;
    }

    public boolean isExitWorldConfirm() {
        return exitWorldConfirm;
    }

    public void setExitWorldConfirm(boolean exitWorldConfirm) {
        this.exitWorldConfirm = exitWorldConfirm;
    }

    public boolean isEnableAutoReconnect() {
        return enableAutoReconnect;
    }

    public void setEnableAutoReconnect(boolean enableAutoReconnect) {
        this.enableAutoReconnect = enableAutoReconnect;
    }

    public int getAutoReconnectWaitTime() {
        return autoReconnectWaitTime;
    }

    public void setAutoReconnectWaitTime(int autoReconnectWaitTime) {
        this.autoReconnectWaitTime = autoReconnectWaitTime;
    }
}
