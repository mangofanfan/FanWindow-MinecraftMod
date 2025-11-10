# FanWindow - Minecraft Mod

Have a different Title Screen !

拥有一个与众不同的标题屏幕!

## 获取

Modrinth: <https://modrinth.com/mod/fan-window>

CurseForge：<https://www.curseforge.com/minecraft/mc-mods/fan-window>

前置模组Cloth Config API，最新版已移除对Fabric API的依赖，建议搭配ModMenu一同食用。

由于芒果帆帆正在学习Java，此模组仅会支持较新的版本的Minecraft，目前只支持Fabric，1.21.x，未来可能会支持更多加载器。

PS：实际上目前并没有用到Fabric API的任何内容，因此似乎理论上可以无缝切换到Forge等……算惹再说吧。

## Maven仓库

如果你（应该没有这种如果吧）想要进一步研究FanWindow，请添加以下Maven仓库：

```
https://maven.fanfan.moe/repository/maven-releases/
```

也就是将你的build.gradle或者build.gradle.kt改成像这样：

```
repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
    
    ...
    
    maven {
        url = "https://maven.fanfan.moe/repository/maven-public/"
    }
}
```

然后添加`cn.mangofanfan.fanwindow:1.2.3`即可。仓库中拥有模组版本1.2.3（MC 1.21.5）。

---

## 图

![New Title Screen](/res/New%20Title%20Screen.png)

![New Background](/res/New%20Background.png)

![New Create World Screen](/res/New%20Create%20World%20Screen.png)

![FanWindow Config](/res/FanWindow%20Config.png)

## 继续定制

更详细的定制背景教程可在<https://fanfan.moe/forum-post/78.html>中查看~

模组的配置页面允许配置各功能的开启与关闭，并在提供的多张背景图片中进行选择、自定义背景或使用原版的全景背景。所有提供的图像的长宽比例均为16:9，会随游戏窗口变化进行中心裁切。

（模组版本1.2.2新增，模组版本1.2.3中的 Resourcepack 模式）可以使用资源包进一步美化游戏页面。在资源包的`assets\fanwindow\textures\artwork\`下可以替换本模组提供的背景图片，一般而言建议使用`custom.png`放在该目录下，然后在游戏中的模组配置页面将背景图片设置为`Custom_png`。该图片尺寸需要为1600*900。

（模组版本1.2.3新增）在 ConfigDir 模式下，可以直接将图片放在模组的配置目录`.minecraft/config/fanwindow`下，命名为`custom.png`。模组会读取图片，并将其作为背景使用。

![Custom Background](/res/Custom%20Background.png)

修改按钮样式等原版UI元素的资源包始终可以生效。下图是一个案例（使用Essential UI资源包+自定义背景和Minecraft徽标）：

![Example Title Screen](/res/Example%20Title%20Screen.png)
