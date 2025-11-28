package cn.mangofanfan.fanwindow.mixin.client;

import cn.mangofanfan.fanwindow.client.config.ConfigManager;
import cn.mangofanfan.fanwindow.client.function.SimpleToastBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Unique
    private long lastDisconnectTime = 0;

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
            if (MinecraftClient.getInstance().currentScreen != null) {
                GameMenuScreen screen = (GameMenuScreen) MinecraftClient.getInstance().currentScreen;
                if (screen.exitButton != null) {
                    screen.exitButton.active = true;
                }
            }
            ci.cancel();
        }
    }
}
