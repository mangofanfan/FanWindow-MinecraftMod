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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin {
    @Unique
    private static long lastDisconnectTime = 0;

    @Inject(method = "disconnect(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/text/Text;)V", at = @At("HEAD"), cancellable = true)
    private static void onDisconnect(MinecraftClient client, Text reasonText, CallbackInfo ci) {
        // 如果没有启用退出世界确认，则无事发生
        if (!ConfigManager.getInstance().config.isExitWorldConfirm()) return;

        // 否则，需要连续尝试退出两次才允许退出
        if (System.currentTimeMillis() - lastDisconnectTime > 30000) {
            lastDisconnectTime = System.currentTimeMillis();
            new SimpleToastBuilder().show(
                    Text.translatable("gui.fanwindow.exit_world_confirm.title"),
                    Text.translatable("gui.fanwindow.exit_world_confirm.description")
            );
            if (MinecraftClient.getInstance().currentScreen != null) {
                GameMenuScreen screen = (GameMenuScreen) MinecraftClient.getInstance().currentScreen;
                if (screen.exitButton != null) {
                    screen.exitButton.active = true;
                }
            }
        }
        else {
            normalDisconnect(reasonText);
        }
        ci.cancel();
    }

    @Unique
    private static void normalDisconnect(Text reasonText) {
        // 首先获取 client 对象
        MinecraftClient client = MinecraftClient.getInstance();
        
        boolean bl = client.isInSingleplayer();
        ServerInfo serverInfo = client.getCurrentServerEntry();
        if (client.world != null) {
            client.world.disconnect(reasonText);
        }

        if (bl) {
            client.disconnectWithSavingScreen();
        } else {
            client.disconnectWithProgressScreen();
        }

        TitleScreen titleScreen = new TitleScreen();
        if (bl) {
            client.setScreen(titleScreen);
        } else if (serverInfo != null && serverInfo.isRealm()) {
            client.setScreen(new RealmsMainScreen(titleScreen));
        } else {
            client.setScreen(new MultiplayerScreen(titleScreen));
        }
    }
}
