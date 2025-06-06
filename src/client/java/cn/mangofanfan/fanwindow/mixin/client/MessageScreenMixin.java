package cn.mangofanfan.fanwindow.mixin.client;

import cn.mangofanfan.fanwindow.client.function.RenderBackgroundImpl;
import cn.mangofanfan.fanwindow.client.screen.ConfigManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MessageScreen.class)
public class MessageScreenMixin extends Screen {
    protected MessageScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true)
    public void onRenderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        if (ConfigManager.getInstance().config.isUseNewBackgroundGlobally()) {
            new RenderBackgroundImpl().renderBackground(context, this.width, this.height);
            ci.cancel();
        }
    }
}
