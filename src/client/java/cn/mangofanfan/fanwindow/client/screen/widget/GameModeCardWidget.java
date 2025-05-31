package cn.mangofanfan.fanwindow.client.screen.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GameModeCardWidget extends ClickableWidget {
    WorldCreator worldCreator;
    WorldCreator.Mode mode;
    Identifier modePicture;
    Screen screen;
    Text strongText;
    int borderColor;
    boolean chosen;

    public GameModeCardWidget(WorldCreator worldCreator, Screen screen) {
        super(0, 0, 60, 90, Text.empty());
        this.worldCreator = worldCreator;
        this.screen = screen;
    }

    public void setMode(WorldCreator.Mode mode) {
        this.mode = mode;
        this.setTooltip(Tooltip.of(mode.getInfo()));
        switch (mode) {
            case CREATIVE:
                borderColor = 0xFF2F4F4F;
                modePicture = Identifier.of("fanwindow", "textures/gamemode/creative.png");
                strongText = Text.translatable("selectWorld.gameMode.creative");
                break;
            case HARDCORE:
                borderColor = 0xFFFFFAFA;
                modePicture = Identifier.of("fanwindow", "textures/gamemode/hardcore.png");
                strongText = Text.translatable("selectWorld.gameMode.hardcore");
                break;
            case SURVIVAL:
                borderColor = 0xFF00BFFF;
                modePicture = Identifier.of("fanwindow", "textures/gamemode/survival.png");
                strongText = Text.translatable("selectWorld.gameMode.survival");
                break;
        }
    }

    public WorldCreator.Mode getMode() {
        return mode;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public boolean isChosen() {
        return this.chosen;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int bgColor = isHovered() ? 0xFF222222 : 0xFF000000;
        int strongColor = isChosen() ? 0xFFFFFFFF : 0xFF000000;
        int strongBgColor = isChosen() ? 0xDD000000 : 0xDDFFFFFF;
        context.fill(getX() + 1, getY() + 1, getX() + width - 1, getY() + height - 1, bgColor);
        context.fill(getX() + 1, getY() + 71, getX() + width - 1, getY() + height - 1, strongBgColor);
        context.drawWrappedText(screen.getTextRenderer(), strongText, getX() + 5, getY() + 76, width - 12, strongColor, false);
        context.drawBorder(getX(), getY(), width, height, borderColor);
        context.drawTexture(RenderLayer::getCelestial, modePicture, getX() + 1, getY() + 1,  0, 0, width - 2, height - 2, width - 2, height - 2);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        worldCreator.setGameMode(mode);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}
}
