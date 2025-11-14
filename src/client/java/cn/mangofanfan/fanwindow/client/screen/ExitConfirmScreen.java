package cn.mangofanfan.fanwindow.client.screen;

import cn.mangofanfan.fanwindow.client.function.VersionedRenderImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * 退出游戏前再次确认使用的确认屏幕
 */
public class ExitConfirmScreen extends Screen {

    private final MinecraftClient client = MinecraftClient.getInstance();

    protected ExitConfirmScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget yesButton = ButtonWidget.builder(Text.translatable("gui.yes"),
                        button -> client.scheduleStop())
                .dimensions(this.width / 2 - 155, this.height / 4 + 96, 150, 20)
                .build();
        ButtonWidget noButton = ButtonWidget.builder(Text.translatable("gui.no"),
                        button -> client.setScreen(new TitleScreen()))
                .dimensions(this.width / 2 + 5, this.height / 4 + 96, 150, 20)
                .build();

        addDrawableChild(yesButton);
        addDrawableChild(noButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);

        new VersionedRenderImpl().fillBackgroundColor(context, this.width / 2 - 155, this.height / 4 + 64, this.width / 2 + 155, this.height / 4 + 90);

        context.drawText(client.textRenderer, Text.translatable("gui.fanwindow.exit_confirm"), this.width / 2 - 150, this.height / 4 + 70, 0xFFFFFFFF, false);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
