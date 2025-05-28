package cn.mangofanfan.fanwindow.client.screen;

import cn.mangofanfan.fanwindow.client.config.BgPicture;
import cn.mangofanfan.fanwindow.client.config.FanWindowConfig;
import com.google.gson.Gson;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * ConfigManager负责本模组的配置的获取、配置文件读写，以及获取配置屏幕。
 * 使用单例模式确保安全使用。
 */
public class ConfigManager {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("fanwindow.json");
    private static ConfigManager instance;
    private final ConfigBuilder configBuilder;
    private final ConfigCategory generalCategory;

    /**
     * 通过ConfigManager中的config属性访问FanWindowConfig。
     */
    public FanWindowConfig config;

    static Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    private ConfigManager() {
        configBuilder = ConfigBuilder.create().setTitle(Text.translatable("fanwindow.config.title"));
        generalCategory = configBuilder.getOrCreateCategory(Text.translatable("fanwindow.config.general"));
        if (config == null) {
            loadConfig();
        }
        initConfigOptions();
        configBuilder.setSavingRunnable(() -> {
            saveConfig();
            logger.info("Saving config of FanWindow.");
        });
    }

    public void loadConfig() {
        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            config = new Gson().fromJson(reader, FanWindowConfig.class);
        } catch (IOException e) {
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
        configBuilder.setParentScreen(parent);
        return configBuilder.build();
    }

    private void initConfigOptions() {
        ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();
        generalCategory.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("fanwindow.config.useNewTitleScreen"),
                                config.isUseNewTitleScreen()
                        )
                        .setDefaultValue(true)
                        .setTooltip(Text.translatable("fanwindow.config.useNewTitleScreen.description"))
                        .setSaveConsumer(newValue -> config.setUseNewTitleScreen(newValue))
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
        generalCategory.addEntry(entryBuilder.startStringDropdownMenu(Text.translatable("fanwindow.config.background"),
                                config.getBgPicture().getPicName())
                        .setDefaultValue(config.getBgPicture().getPicName())
                        .setSelections(BgPicture.getValues())
                        .setTooltip(Text.translatable("fanwindow.config.background.description"))
                        .setSaveConsumer(this::saveBgPicture)
                        .build());
        generalCategory.addEntry(entryBuilder.startTextDescription(Text.translatable("fanwindow.config.description")).build());
    }

    private void saveBgPicture(String value) {
        try {
            config.setBgPicture(value);
        } catch (IllegalArgumentException e) {
            MinecraftClient client = MinecraftClient.getInstance();
            client.getToastManager().add(
                    SystemToast.create(client,
                            SystemToast.Type.UNSECURE_SERVER_WARNING,
                            Text.translatable("fanwindow.config.background.errorToast.title"),
                            Text.translatable("fanwindow.config.background.errorToast.description"))
            );
            logger.error("IllegalArgumentException occurred when saving bg picture!", e);
        }
    }
}
