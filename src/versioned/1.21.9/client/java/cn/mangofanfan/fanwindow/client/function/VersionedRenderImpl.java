package cn.mangofanfan.fanwindow.client.function;

import cn.mangofanfan.fanwindow.client.config.ConfigManager;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class VersionedRenderImpl extends VersionedRender {
    public void renderBackground(DrawContext context, int[] textureSize, int width, int height, Identifier bgTexture) {
        // 确保渲染背景图片正确，即图片比例不变，且在裁切时总是保持中心位置。
        int[] regionSize = {0, 0};
        double var1 = (double) textureSize[0] / textureSize[1];
        double var2 = (double) width / height;
        if (var1 == var2) {
            regionSize = textureSize;
        }
        else if (var1 < var2) {
            regionSize[0] = textureSize[0];
            regionSize[1] = (int) (textureSize[1] / var2 * var1);
        }
        else {
            regionSize[0] = (int) (textureSize[0] / var1 * var2);
            regionSize[1] = textureSize[1];
        }
        context.drawTexture(RenderPipelines.GUI_TEXTURED, bgTexture, 0, 0, (float) (textureSize[0] - regionSize[0]) / 2, (float) (textureSize[1] - regionSize[1]) / 2, width, height, regionSize[0], regionSize[1], textureSize[0], textureSize[1]);
    }

    /**
     * 简化的公开方法，从 ConfigManager 获取背景图片和尺寸
     * @param context DrawContext
     * @param width 游戏屏幕宽度
     * @param height 游戏屏幕高度
     */
    public void renderBackground(DrawContext context, int width, int height) {
        ConfigManager configManager = ConfigManager.getInstance();
        renderBackground(context, configManager.getBackgroundTextureSize(), width, height, configManager.getBackgroundTexture());
    }

    @Override
    public void fillBackgroundColor(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        context.fill(x1, y1, x2, y2, color);
    }

    /**
     * @param context DrawContext
     */
    @Override
    public void fillBackgroundColor(DrawContext context, int x1, int y1, int x2, int y2) {
        fillBackgroundColor(context, x1, y1, x2, y2, 0x88000000);
    }
}
