package cn.mangofanfan.fanwindow.client.screen;

import cn.mangofanfan.fanwindow.client.config.FanWindowConfig;
import com.google.gson.Gson;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("fanwindow.json");
    private static ConfigManager instance;
    private final ConfigBuilder configBuilder;
    private final ConfigCategory generalCategory;

    public FanWindowConfig config;

    static Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    public ConfigManager() {
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
                        config.useNewTitleScreen
                )
                .setDefaultValue(false)
                .setTooltip(Text.translatable("fanwindow.config.useNewTitleScreen.description"))
                        .setSaveConsumer(newValue -> config.useNewTitleScreen = newValue)
                .build());
    }
}
