package cn.mangofanfan.fanwindow.client.config;

import cn.mangofanfan.fanwindow.client.function.LocalBackgroundTextureIdentifier;
import com.google.gson.Gson;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


/**
 * ConfigManager负责本模组的配置的获取、配置文件读写，以及获取配置屏幕。
 * 使用单例模式确保安全使用。
 */
public class ConfigManager {
    private static final Path CUSTOM_IMAGE = FabricLoader.getInstance().getConfigDir().resolve("fanwindow/custom.png");
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("fanwindow/fanwindow.json");
    private static ConfigManager instance;
    private final ConfigBuilder configBuilder;
    private ConfigCategory generalCategory;
    private ConfigCategory customBackgroundCategory;
    private LocalBackgroundTextureIdentifier localBackgroundTextureIdentifier;

    /**
     * 通过ConfigManager中的config属性访问FanWindowConfig。
     */
    public FanWindowConfig config;

    static Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    private ConfigManager() {
        configBuilder = ConfigBuilder.create().setTitle(Text.translatable("fanwindow.config.title"));
        loadConfig();
        if (config.getCustomPictureMode().equals(CustomPictureMode.ConfigDir)) {
            localBackgroundTextureIdentifier = new LocalBackgroundTextureIdentifier(CUSTOM_IMAGE);
        }
        configBuilder.setSavingRunnable(() -> {
            saveConfig();
            logger.info("Saving config of Fan Window.");
        });
    }

    public void loadConfig() {
        // 若配置文件目录fanwindow已存在则不创建，否则创建
        try {
            Files.createDirectory(FabricLoader.getInstance().getConfigDir().resolve("fanwindow"));
        } catch (IOException ignored) {}

        // 初始化配置文件
        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            config = new Gson().fromJson(reader, FanWindowConfig.class);
        } catch (IOException | NoSuchFieldError e) {
            config = new FanWindowConfig(); // 创建默认配置
            saveConfig();
        }
    }

    public void saveConfig() {
        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            new Gson().toJson(config, writer);
        } catch (IOException e) {
            logger.error("Exception occurred when saving FanWindow config !{}", String.valueOf(e));
        }
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public Screen getScreen(Screen parent) {
        logger.debug("Getting ConfigScreen instance.");
        initConfigOptions();
        configBuilder.setParentScreen(parent);
        return configBuilder.build();
    }

    /**
     * 每次获取设置屏幕之前，都需要调用此方法来重新加载所有配置项
     * 否则，配置屏幕会出现保存逻辑异常等问题
     */
    private void initConfigOptions() {
        // 如果已经加载过配置项目（已经获取过一次配置屏幕）则清空已有配置项
        if (generalCategory != null && customBackgroundCategory != null) {
            generalCategory.removeCategory();
            customBackgroundCategory.removeCategory();
        }

        // 加载配置项目
        ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();
        generalCategory = configBuilder.getOrCreateCategory(Text.translatable("fanwindow.config.general"));
        customBackgroundCategory = configBuilder.getOrCreateCategory(Text.translatable("fanwindow.config.customBackground"));
        generalCategory.addEntry(entryBuilder.startTextDescription(Text.translatable("fanwindow.config.description")).build());
        generalCategory.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("fanwindow.config.useNewTitleScreen"),
                                config.isUseNewTitleScreen()
                        )
                        .setDefaultValue(true)
                        .setTooltip(Text.translatable("fanwindow.config.useNewTitleScreen.description"))
                        .setSaveConsumer(newValue -> config.setUseNewTitleScreen(newValue))
                        .build());
        generalCategory.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("fanwindow.config.disableVanillaTitleScreen"),
                                config.isDisableVanillaTitleScreen()
                        )
                        .setDefaultValue(false)
                        .setTooltip(Text.translatable("fanwindow.config.disableVanillaTitleScreen.description"))
                        .setSaveConsumer(newValue -> config.setDisableVanillaTitleScreen(newValue))
                        .build());
        generalCategory.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("fanwindow.config.useNewCreateWorldScreen"),
                                config.isUseNewCreateWorldScreen()
                        )
                        .setDefaultValue(true)
                        .setTooltip(Text.translatable("fanwindow.config.useNewCreateWorldScreen.description"))
                        .setSaveConsumer(newValue -> config.setUseNewCreateWorldScreen(newValue))
                        .build());
        generalCategory.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("fanwindow.config.useNewBackgroundInNewScreen"),
                                config.isUseNewBackgroundInNewScreen()
                        )
                        .setDefaultValue(true)
                        .setTooltip(Text.translatable("fanwindow.config.useNewBackgroundInNewScreen.description"))
                        .setSaveConsumer(newValue -> config.setUseNewBackgroundInNewScreen(newValue))
                        .build());
        generalCategory.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("fanwindow.config.useNewBackgroundGlobally"),
                                config.isUseNewBackgroundGlobally()
                        )
                        .setDefaultValue(true)
                        .setTooltip(Text.translatable("fanwindow.config.useNewBackgroundGlobally.description"))
                        .setSaveConsumer(newValue -> config.setUseNewBackgroundGlobally(newValue))
                        .build());
        customBackgroundCategory.addEntry(entryBuilder.startDropdownMenu(
                                Text.translatable("fanwindow.config.customBackgroundImageMode"),
                                DropdownMenuBuilder.TopCellElementBuilder.of(
                                        config.getCustomPictureMode(),
                                        this::stringToCustomPictureMode,
                                        this::customPictureModeToString
                                )
                        )
                        .setDefaultValue(CustomPictureMode.Disabled)
                        .setTooltip(Text.translatable("fanwindow.config.customBackgroundImageMode.description"))
                        .setSelections(List.of(CustomPictureMode.values()))
                        .setSaveConsumer(this::saveCustomPictureMode)
                        .build());
        customBackgroundCategory.addEntry(entryBuilder.startTextDescription(Text.translatable("fanwindow.config.customBackground.description")).build());
        customBackgroundCategory.addEntry(entryBuilder.startDropdownMenu(
                        Text.translatable("fanwindow.config.background"),
                        DropdownMenuBuilder.TopCellElementBuilder.of(
                                config.getBgPicture(),
                                this::stringToBgPicture,
                                this::bgPictureToText
                        )
                )
                .setDefaultValue(DefaultBgPictureGetter.getDefaultBgPicture())
                .setTooltip(Text.translatable("fanwindow.config.background.description"))
                .setSelections(List.of(BgPicture.values()))
                .setSaveConsumer(this::saveBgPicture)
                .build());
        customBackgroundCategory.addEntry(
                entryBuilder.startTextDescription(
                        Text.translatable("fanwindow.config.background.versionFeaturedDescription",
                                DefaultBgPictureGetter.getDefaultBgPicture().getPicName(), FabricLoader.getInstance().getRawGameVersion())
                ).build());
    }

    private void saveBgPicture(BgPicture bgPicture) {
        config.setBgPicture(bgPicture);
    }

    private void saveCustomPictureMode(CustomPictureMode customPictureMode) {
        config.setCustomPictureMode(customPictureMode);
        if (config.getCustomPictureMode().equals(CustomPictureMode.ConfigDir)) {
            localBackgroundTextureIdentifier = new LocalBackgroundTextureIdentifier(CUSTOM_IMAGE);
        }
    }

    private BgPicture stringToBgPicture(String value) {
        for (BgPicture bgPicture : BgPicture.values()) {
            if (bgPicture.name().equalsIgnoreCase(value)) {
                return bgPicture;
            }
        }
        return null;
    }

    private Text bgPictureToText(BgPicture bgPicture) {
        return Text.of(bgPicture.name());
    }

    private CustomPictureMode stringToCustomPictureMode(String value) {
        for (CustomPictureMode customPictureMod : CustomPictureMode.values()) {
            if (customPictureMod.name().equalsIgnoreCase(value)) {
                return customPictureMod;
            }
        }
        return null;
    }

    private Text customPictureModeToString(CustomPictureMode customPictureMode) {
        return Text.of(customPictureMode.name());
    }

    public Identifier getBackgroundTexture() {
        Identifier identifier = null;
        switch (config.getCustomPictureMode()) {
            case Disabled -> identifier = Identifier.of("fanwindow", config.getBgPicture().getPath());
            case ResourcePack -> identifier = Identifier.of("fanwindow", "textures/artwork/custom.png");
            case ConfigDir -> identifier = localBackgroundTextureIdentifier.getIdentifier();
        }
        return identifier;
    }

    public int[] getBackgroundTextureSize() {
        int[] textureSize = {0, 0};
        switch (config.getCustomPictureMode()) {
            case Disabled ->  textureSize = config.getBgPicture().getPicSize();
            case ResourcePack -> textureSize = new int[]{1600, 900};
            case ConfigDir ->  textureSize = localBackgroundTextureIdentifier.getTextureSize();
        }
        return  textureSize;
    }
}
