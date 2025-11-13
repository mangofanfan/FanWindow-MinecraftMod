package cn.mangofanfan.fanwindow.mixin.client;

import cn.mangofanfan.fanwindow.client.config.ConfigManager;
import cn.mangofanfan.fanwindow.client.function.SimpleToastBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Unique
    private long lastDisconnectTime = 0;
    @Unique
    Logger logger = LoggerFactory.getLogger("MinecraftClientMixin");

    @Inject(method = "disconnect(Lnet/minecraft/text/Text;)V", at = @At("HEAD"), cancellable = true)
    public void onDisconnect(Text reasonText, CallbackInfo ci) {
        // 如果没有启用退出世界确认，则无事发生
        if (!ConfigManager.getInstance().config.isExitWorldConfirm()) return;

        // 否则，需要连续尝试退出两次才允许退出
        if (System.currentTimeMillis() - lastDisconnectTime > 30000) {
            lastDisconnectTime = System.currentTimeMillis();
            new SimpleToastBuilder().show(
                    Text.translatable("gui.fanwindow.exit_world_confirm.title"),
                    Text.translatable("gui.fanwindow.exit_world_confirm.description")
            );
            logger.info("Re-click disconnect button in 30s to confirm...");
            if (MinecraftClient.getInstance().currentScreen != null) {
                GameMenuScreen screen = (GameMenuScreen) MinecraftClient.getInstance().currentScreen;
                if (screen.exitButton != null) {
                    screen.exitButton.active = true;
                }
            }
        }
        else {
            normalDisconnect(reasonText);
            logger.info("Exit world confirmed.");
        }
        ci.cancel();
    }

    @Unique
    public void normalDisconnect(Text reasonText) {
        // 首先获取 self 对象
        MinecraftClient self = (MinecraftClient)(Object)this;
        
        boolean bl = self.isInSingleplayer();
        ServerInfo serverInfo = self.getCurrentServerEntry();
        if (self.world != null) {
            self.world.disconnect(reasonText);
        }

        if (bl) {
            self.disconnectWithSavingScreen();
        } else {
            self.disconnectWithProgressScreen();
        }

        TitleScreen titleScreen = new TitleScreen();
        if (bl) {
            self.setScreen(titleScreen);
        } else if (serverInfo != null && serverInfo.isRealm()) {
            self.setScreen(new RealmsMainScreen(titleScreen));
        } else {
            self.setScreen(new MultiplayerScreen(titleScreen));
        }
    }
}
