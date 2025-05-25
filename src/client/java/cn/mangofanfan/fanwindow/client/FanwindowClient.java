package cn.mangofanfan.fanwindow.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FanwindowClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        Logger logger = LoggerFactory.getLogger("FanWindowClient");
        GlobalState globalState = GlobalState.getInstance();

        if (FabricLoader.getInstance().isModLoaded("modmenu")) {
            logger.info("ModMenu is loaded. Specifical support enabled.");
            globalState.setModMenuSupport(true);
        }
        else {
            logger.info("Not found ModMenu.");
        }
    }
}
