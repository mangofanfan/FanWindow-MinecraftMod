package cn.mangofanfan.fanwindow.client.screen;

import cn.mangofanfan.fanwindow.client.GlobalState;
import cn.mangofanfan.fanwindow.client.config.ConfigManager;
import cn.mangofanfan.fanwindow.client.function.RenderBackgroundImpl;
import cn.mangofanfan.fanwindow.client.function.SimpleToastBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.AccessibilityOnboardingButtons;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class NewTitleScreen extends Screen {
    // 定位点
    private int cenX;
    private int cenY;

    private final LogoDrawer logoDrawer;
    ConfigManager configManager;
    SimpleToastBuilder toastBuilder;

    // 单人游戏
    private volatile ButtonWidget singlePlayerButton;
    // 多人游戏
    private volatile ButtonWidget multiPlayerButton;
    // 选项
    private volatile ButtonWidget optionsButton;
    // 退出
    private volatile ButtonWidget quitButton;
    // 切换按钮
    private volatile ButtonWidget toggleButton;
    // 语言
    private volatile TextIconButtonWidget languageOptionButton;
    // 无障碍
    private volatile TextIconButtonWidget accessibilityOptionButton;
    // 版权
    private volatile ButtonWidget copyrightButton;
    // 设置按钮 或 模组菜单按钮
    private volatile ButtonWidget modsButton;

    private final GlobalState globalState;
    private static final Logger logger = LoggerFactory.getLogger(NewTitleScreen.class);
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public NewTitleScreen(Text title) {
        super(title);
        globalState = GlobalState.getInstance();
        logoDrawer = new LogoDrawer(false);
        configManager = ConfigManager.getInstance();
        toastBuilder = new SimpleToastBuilder();
    }

    @Override
    protected void init() {
        super.init();

        cenX = width - 160;
        cenY = height - 160;

        this.singlePlayerButton = ButtonWidget.builder(Text.translatable("menu.singleplayer"),
                button -> client.setScreen(new SelectWorldScreen(this)))
                .dimensions(cenX, cenY, 60, 60)
                .build();
        this.multiPlayerButton = ButtonWidget.builder(Text.translatable("menu.multiplayer"),
                button -> {
                    Screen screen = client.options.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this);
                    client.setScreen(screen);
                })
                .dimensions(cenX + 65, cenY, 60, 60)
                .build();
        this.optionsButton = ButtonWidget.builder(Text.translatable("menu.options"),
                button -> client.setScreen(new OptionsScreen(this, client.options)))
                .dimensions(cenX, cenY + 65, 60, 60)
                .build();
        this.quitButton = ButtonWidget.builder(Text.translatable("menu.quit"),
                button -> client.scheduleStop())
                .dimensions(cenX + 65, cenY + 65, 60, 60)
                .build();
        this.toggleButton = this.getToggleButtonBuilder()
                .dimensions(cenX + 130, cenY, 27, 27)
                .build();
        this.languageOptionButton = AccessibilityOnboardingButtons.createLanguageButton(27,
                button -> client.setScreen(new LanguageOptionsScreen(this, client.options, client.getLanguageManager())),
                true);
        this.languageOptionButton.setHeight(27);
        this.languageOptionButton.setPosition(cenX + 130, cenY + 33);
        this.accessibilityOptionButton = AccessibilityOnboardingButtons.createAccessibilityButton(27,
                button -> client.setScreen(new AccessibilityOptionsScreen(this, client.options)),
                true);
        this.accessibilityOptionButton.setHeight(27);
        this.accessibilityOptionButton.setPosition(cenX + 130, cenY + 65);
        this.copyrightButton = ButtonWidget.builder(Text.of("©"),
                button -> client.setScreen(new CreditsAndAttributionScreen(this)))
                .dimensions(cenX + 130, cenY + 98, 27, 27)
                .build();

        this.addDrawableChild(singlePlayerButton);
        this.addDrawableChild(multiPlayerButton);
        this.addDrawableChild(optionsButton);
        this.addDrawableChild(quitButton);
        this.addDrawableChild(toggleButton);
        this.addDrawableChild(languageOptionButton);
        this.addDrawableChild(accessibilityOptionButton);
        this.addDrawableChild(copyrightButton);

        // 若未禁用原版标题屏幕，则配置与模组菜单按钮应当位于此处
        if (!configManager.config.isDisableVanillaTitleScreen()) {
            this.modsButton = this.getModsButtonBuilder(Text.translatable("category.modmenu.name"), Text.translatable("fanwindow.config"))
                    .dimensions(cenX, cenY + 130, 60, 27)
                    .build();
            this.addDrawableChild(modsButton);
        }
    }

    /**
     * <p>获取足以打开 ModMenu 提供的 ModsScreen 屏幕的按钮 Builder。</p>
     * <p>若 ModMenu 未加载，本方法将抛出 ClassNotFoundException 错误，应当在调用时接收并处理此错误。</p>
     * @param name 按钮上的文本
     * @return 按钮 Builder
     */
    private ButtonWidget.@NotNull Builder getModsScreenButtonBuilder(Text name) throws ClassNotFoundException {
        Class<?> ModsScreen = Class.forName("com.terraformersmc.modmenu.gui.ModsScreen");
        Class<?>[] paramTypes = { Screen.class };
        ButtonWidget.Builder builder = ButtonWidget.builder(name,
                button -> {
                    try {
                        client.setScreen((Screen) ModsScreen.getDeclaredConstructor(paramTypes).newInstance(this));
                        logger.debug("Open ModsScreen from FanWindow Title Screen.");
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        logger.error("Could not instantiate ModsScreen while ModMenu is already loaded : ", e);
                        toastBuilder.show(
                                Text.translatable("fanwindow.modmenu.modsScreenFailed.title"),
                                Text.translatable("fanwindow.modmenu.modsScreenFailed.description"));
                    }
                });
        builder.dimensions(cenX, cenY + 130, 60, 27);
        return builder;
    }

    /**
     * 根据 ModMenu 加载情况，获取不同的按钮 Builder。
     * @param name1 若 ModMenu 已加载，获取的打开 ModsScreen 的按钮上的文本
     * @param name2 若 ModMenu 未加载，获取的打开配置屏幕的按钮上的文本
     * @return 按钮 Builder
     */
    private ButtonWidget.@NotNull Builder getModsButtonBuilder(Text name1, Text name2) {
        // 如果有ModMenu则添加打开ModsScreen的按钮
        if (globalState.isModMenuSupport()) {
            try {
                return this.getModsScreenButtonBuilder(name1);
            } catch (ClassNotFoundException e) {
                logger.error("Could not load ModsScreen while ModMenu is already loaded : ", e);
                throw new RuntimeException(e);
            }
        }
        // 否则添加打开本模组配置页面的按钮
        else {
            return ButtonWidget.builder(name2,
                            button -> {
                                client.setScreen(configManager.getScreen(this));
                                logger.debug("Open FanWindow ConfigScreen from FanWindow Title Screen.");
                            });
        }
    }

    /**
     * 根据设置，获取切换标题屏幕或 {@link NewTitleScreen#getModsButtonBuilder(Text, Text)} 的按钮 Builder。
     * @return 按钮 Builder
     */
    private ButtonWidget.@NotNull Builder getToggleButtonBuilder() {
        // 若未禁用原版标题屏幕，则创建切换标题屏幕的按钮
        if (!configManager.config.isDisableVanillaTitleScreen()) {
            return ButtonWidget.builder(Text.of("</>"),
                    button -> {
                        GlobalState globalState = GlobalState.getInstance();
                        globalState.setStarted(true);
                        globalState.setNewMainWindowInUse(false);
                        client.setScreen(new TitleScreen());
                    });
        }
        // 否则，创建打开模组菜单或配置屏幕的按钮
        else {
            return getModsButtonBuilder(Text.of("</>"), Text.of("</>"));
        }
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        // 强制重新初始化布局
        this.clearChildren();
        this.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        logoDrawer.draw(context, width / 5 * 3, 1.0F, height - 50);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (configManager.config.isUseNewBackgroundGlobally() || (!configManager.config.isUseNewBackgroundGlobally()) && !configManager.config.isUseNewBackgroundInNewScreen()) {
            super.renderBackground(context, mouseX, mouseY, deltaTicks);
        }
        else {
            new RenderBackgroundImpl().renderBackground(context, width, height);
        }
    }

    @Override
    protected void renderDarkening(DrawContext context) {}

    @Override
    public void close() {
        if (client != null) {
            client.setScreen(new TitleScreen());
        }
    }
}