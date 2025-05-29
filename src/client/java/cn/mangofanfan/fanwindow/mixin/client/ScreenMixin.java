package cn.mangofanfan.fanwindow.mixin.client;

import cn.mangofanfan.fanwindow.client.config.BgPicture;
import cn.mangofanfan.fanwindow.client.function.RenderBackground;
import cn.mangofanfan.fanwindow.client.screen.ConfigManager;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractParentElement implements Drawable {
    @Unique
    ConfigManager configManager;

    /*
    在这里使用 @Shadow 获取例如 client 之类的属性会导致游戏内渲染问题，因此在下面重新获取了 client 实例。
    我也不知道为什么喵！
    */

    @Inject(method = "renderPanoramaBackground", at = @At("HEAD"), cancellable = true)
    private void onRenderBackground(CallbackInfo ci,
                                    @Local(argsOnly = true) DrawContext context,
                                    @Local(argsOnly = true) float deltaTicks) {
        MinecraftClient client = MinecraftClient.getInstance();
        configManager = ConfigManager.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        if (client.world == null) {
            Logger logger = LoggerFactory.getLogger("ScreenMixin");
            logger.trace("ScreenMixin is ready to change global background.");
            if (configManager.config.isUseNewBackgroundGlobally()) {
                BgPicture bgPicture = configManager.config.getBgPicture();
                int[] textureSize = bgPicture.getPicSize();
                Identifier bgTexture = Identifier.of("fanwindow", bgPicture.getPath());
                RenderBackground.renderBackground(context, textureSize, width, height, bgTexture);
                logger.trace("ScreenMixin background has been rendered.");
                ci.cancel();
            }
            else {
                logger.trace("According to config, ScreenMixin return.");
            }
        }
    }
}
