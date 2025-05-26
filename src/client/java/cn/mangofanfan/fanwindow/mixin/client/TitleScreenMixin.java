package cn.mangofanfan.fanwindow.mixin.client;

import cn.mangofanfan.fanwindow.client.GlobalState;
import cn.mangofanfan.fanwindow.client.screen.MainWindowScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Unique
    Logger logger;
    @Unique
    GlobalState globalState;
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void onInit(CallbackInfo ci) {
        globalState = GlobalState.getInstance();
        // create toggle button
        ButtonWidget toggleButton = ButtonWidget.builder(Text.of(">_<"),
                        (ButtonWidget button) -> {
            globalState.setNewMainWindowInUse(true);
            MinecraftClient.getInstance().setScreen(new MainWindowScreen(Text.of(">_<")));
        })
                .dimensions(0, 0, 30, 30).build();
        this.addDrawableChild(toggleButton);

        logger = LoggerFactory.getLogger("TitleScreenMixin");
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void afterInit(CallbackInfo ci) {
        // toggle on once
        if (!globalState.isStarted()) {
            logger.info("Change to MainWindowScreen when launching...");
            MinecraftClient.getInstance().setScreen(new MainWindowScreen(Text.of(">_<")));
        } else if (globalState.isNewMainWindowInUse()) {
            logger.info("Back to MainWindowScreen...");
            MinecraftClient.getInstance().setScreen(new MainWindowScreen(Text.of(">_<")));
        } else {
            logger.info("Not launching, nothing to do.");
        }
    }
}