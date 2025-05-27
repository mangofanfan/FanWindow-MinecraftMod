package cn.mangofanfan.fanwindow.client.config;


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
