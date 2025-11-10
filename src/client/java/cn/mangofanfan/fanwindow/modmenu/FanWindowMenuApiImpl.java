package cn.mangofanfan.fanwindow.modmenu;

import cn.mangofanfan.fanwindow.client.config.ConfigManager;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/**
 * 仅负责向ModMenu提供本模组的配置页面。
 */
public class FanWindowMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> ConfigManager.getInstance().getScreen(parent);
    }
}
