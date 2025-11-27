package cn.mangofanfan.fanwindow.mixin.client;

import cn.mangofanfan.fanwindow.client.stats.ClientStats;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public abstract class DisconnectedScreenMixin {
    @Shadow
    @Final
    private Screen parent;

    @Shadow
    @Final
    private DirectionalLayoutWidget grid;

    // 感谢 Jetbrains AI，感谢 Gemini 3 Pro，J门——G门——
    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;refreshPositions()V"))
    private void injectBeforeGridRefresh(CallbackInfo ci) {
        ButtonWidget reConnectButton = ButtonWidget.builder(
                Text.translatable("gui.fanwindow.reconnect"),
                button -> {
                    ServerInfo info = ClientStats.getLastServerEntry();
                    ConnectScreen.connect(
                            this.parent,
                            MinecraftClient.getInstance(),
                            ServerAddress.parse(info.address),
                            info,
                            false,
                            null);
                }
        ).width(200).build();
        this.grid.add(reConnectButton);
    }
}
