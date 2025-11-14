package cn.mangofanfan.fanwindow.client.function;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

/**
 * 因为 Minecraft 版本变化而需要差异化的方法
 */
public abstract class VersionedRender {
    public abstract void renderBackground(DrawContext context, int[] textureSize, int width, int height, Identifier bgTexture);

    /**
     * 简化的公开方法，从 ConfigManager 获取背景图片和尺寸
     * @param context DrawContext
     * @param width 游戏屏幕宽度
     * @param height 游戏屏幕高度
     */
    public abstract void renderBackground(DrawContext context, int width, int height);

    public abstract void fillBackgroundColor(DrawContext context, int x1, int y1, int x2, int y2, int color);

    public abstract void fillBackgroundColor(DrawContext context, int x1, int y1, int x2, int y2);
}
