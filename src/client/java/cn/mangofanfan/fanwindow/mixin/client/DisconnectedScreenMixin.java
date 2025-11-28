package cn.mangofanfan.fanwindow.mixin.client;

import cn.mangofanfan.fanwindow.client.GlobalState;
import cn.mangofanfan.fanwindow.client.config.ConfigManager;
import cn.mangofanfan.fanwindow.client.config.FanWindowConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Mixin(DisconnectedScreen.class)
public abstract class DisconnectedScreenMixin extends Screen {
    protected DisconnectedScreenMixin(Text title) {
        super(title);
    }

    @Shadow
    @Final
    private Screen parent;

    @Shadow
    @Final
    private DirectionalLayoutWidget grid;

    @Unique
    ButtonWidget reConnectButton;

    @Unique
    FanWindowConfig config = ConfigManager.getInstance().config;

    @Unique
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // 感谢 Jetbrains AI，感谢 Gemini 3 Pro，J门——G门——
    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;refreshPositions()V"))
    private void injectBeforeGridRefresh(CallbackInfo ci) {
        reConnectButton = ButtonWidget.builder(
                config.isEnableAutoReconnect()
                        ? Text.translatable("gui.fanwindow.auto_reconnect", config.getAutoReconnectWaitTime())
                        : Text.translatable("gui.fanwindow.reconnect"),
                button -> reConnect()
        ).width(200).build();
        this.grid.add(reConnectButton);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void initEnd(CallbackInfo ci) {
        if (config.isEnableAutoReconnect()) {
            executor.execute(this::timerTask);
        }
    }

    @Unique
    private void timerTask() {
        int time = config.getAutoReconnectWaitTime();
        try {
            if (client != null) {
                while (time-- > 0) {
                    Thread.sleep(1000);
                        int finalTime = time;
                        client.execute(() ->
                                this.reConnectButton.setMessage(Text.translatable("gui.fanwindow.auto_reconnect", finalTime))
                        );
                    }
                client.execute(this::reConnect);
            }
        } catch (InterruptedException ignored) { }
    }

    @Unique
    private void reConnect() {
        ServerInfo info = GlobalState.getInstance().getLastServerEntry();
        ConnectScreen.connect(
                this.parent,
                MinecraftClient.getInstance(),
                ServerAddress.parse(info.address),
                info,
                false,
                null);
    }

    @Override
    public void close() {
        executor.close();
        super.close();
    }
}
