package cn.mangofanfan.fanwindow.client;

import cn.mangofanfan.fanwindow.client.function.MinecraftVersionGetter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FanwindowClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        Logger logger = LoggerFactory.getLogger("FanWindowClient");
        GlobalState globalState = GlobalState.getInstance();
        logger.info("Running on Minecraft Version: {}", MinecraftVersionGetter.getMinecraftVersion());

        if (FabricLoader.getInstance().isModLoaded("modmenu")) {
            logger.info("ModMenu is loaded. Specifical support enabled.");
            globalState.setModMenuSupport(true);
        }
        else {
            logger.info("Not found ModMenu.");
        }
    }
}
