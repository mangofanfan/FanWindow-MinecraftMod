package cn.mangofanfan.fanwindow.client.function;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class RenderBackground {
    public static void renderBackground(DrawContext context, int[] textureSize, int width, int height, Identifier bgTexture) {
        // 确保渲染背景图片正确，即图片比例不变，且在裁切时总是保持中心位置。
        int[] regionSize = {0, 0};
        double var1 = (double) textureSize[0] / textureSize[1];
        double var2 = (double) width / height;
        if (var1 == var2) {
            regionSize = textureSize;
        } else if (var1 < var2) {
            regionSize[0] = textureSize[0];
            regionSize[1] = (int) (textureSize[1] / var2 * var1);
        }
        else {
            regionSize[0] = (int) (textureSize[0] / var1 * var2);
            regionSize[1] = textureSize[1];
        }
        context.drawTexture(bgTexture, 0, 0, width, height, (float) (textureSize[0] - regionSize[0]) / 2, (float) (textureSize[1] - regionSize[1]) / 2, regionSize[0], regionSize[1], textureSize[0], textureSize[1]);
    }
}
