# FanWindow - Minecraft Mod

Have a different Title Screen !

拥有一个与众不同的标题屏幕!

## 获取

Modrinth: <https://modrinth.com/mod/fan-window>

CurseForge：<https://www.curseforge.com/minecraft/mc-mods/fan-window>

前置模组Cloth Config API，最新版已移除对Fabric API的依赖，建议搭配ModMenu一同食用。

由于芒果帆帆正在学习Java，此模组仅会支持较新的版本的Minecraft，目前只支持Fabric，1.21.x，未来可能会支持更多加载器。

PS：实际上目前并没有用到Fabric API的任何内容，因此似乎理论上可以无缝切换到Forge等……算惹再说吧。

---

## 图

![New Title Screen](/res/New%20Title%20Screen.png)

![New Background](/res/New%20Background.png)

![New Create World Screen](/res/New%20Create%20World%20Screen.png)

![FanWindow Config](/res/FanWindow%20Config.png)

## 继续定制

模组的配置页面允许配置各功能的开启与关闭，并在提供的多张背景图片中进行选择、自定义背景或使用原版的全景背景。所有提供的图像的长宽比例均为16:9，会随游戏窗口变化进行中心裁切。

可以使用资源包进一步美化游戏页面。在资源包的`assets\fanwindow\textures\artwork\`下可以替换本模组提供的背景图片，一般而言建议使用`custom.png`放在该目录下，然后在游戏中的模组配置页面将背景图片设置为`Custom_png`。该图片尺寸需要为1600*900。

此外，修改按钮样式等原版UI元素的资源包也可以生效。下图是一个案例（使用Essential UI资源包+自定义背景和Minecraft徽标）：

![Example Title Screen](/res/Example%20Title%20Screen.png)
