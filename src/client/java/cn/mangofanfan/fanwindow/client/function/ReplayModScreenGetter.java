package cn.mangofanfan.fanwindow.client.function;

import com.replaymod.replay.ReplayModReplay;
import com.replaymod.replay.gui.screen.GuiReplayViewer;
import net.minecraft.client.gui.screen.Screen;

/**
* 一个工具类，提供了打开 ReplayMod录像回放查看器屏幕的方法。
* 当 replaymod 已加载时被调用。调用前应当做严格检查。
*/
public class ReplayModScreenGetter {
    public static Screen getReplayModScreen() {
        return new GuiReplayViewer(ReplayModReplay.instance).toMinecraft();
    }
}
