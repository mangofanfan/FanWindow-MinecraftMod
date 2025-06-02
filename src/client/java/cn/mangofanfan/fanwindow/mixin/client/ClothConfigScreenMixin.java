package cn.mangofanfan.fanwindow.mixin.client;

import cn.mangofanfan.fanwindow.client.config.BgPicture;
import cn.mangofanfan.fanwindow.client.function.RenderBackground;
import cn.mangofanfan.fanwindow.client.screen.ConfigManager;
import com.llamalad7.mixinextras.sugar.Local;
import me.shedaniel.clothconfig2.gui.ClothConfigScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClothConfigScreen.class)
public class ClothConfigScreenMixin extends Screen {

    protected ClothConfigScreenMixin(Text title) {
        super(title);
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lme/shedaniel/clothconfig2/gui/ClothConfigScreen;renderPanoramaBackground(Lnet/minecraft/client/gui/DrawContext;F)V")
    )
    public void onRenderBackground(ClothConfigScreen instance, DrawContext context, float v, @Local(argsOnly = true) float deltaTick) {
        if (ConfigManager.getInstance().config.isUseNewBackgroundGlobally()) {
            BgPicture bgPicture = ConfigManager.getInstance().config.getBgPicture();
            Identifier bgTexture = Identifier.of("fanwindow", bgPicture.getPath());
            RenderBackground.renderBackground(context, bgPicture.getPicSize(), instance.width, instance.height, bgTexture);
        }
        else {
            this.renderPanoramaBackground(context, deltaTick);
        }
    }
}
