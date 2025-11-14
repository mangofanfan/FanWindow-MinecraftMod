package cn.mangofanfan.fanwindow.mixin.client;

import cn.mangofanfan.fanwindow.client.config.ConfigManager;
import cn.mangofanfan.fanwindow.client.screen.NewCreateWorldScreenGameTab;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin extends Screen {
    @Shadow
    private TabNavigationWidget tabNavigation;
    @Final
    @Shadow
    private TabManager tabManager;
    @Final
    @Shadow
    private ThreePartsLayoutWidget layout;
    @Final
    @Shadow
    WorldCreator worldCreator;

    @Unique
    ConfigManager configManager = ConfigManager.getInstance();

    protected CreateWorldScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    protected void onInit(CallbackInfo ci) {
        // 检查配置
        if (!configManager.config.isUseNewCreateWorldScreen()) {
            return;
        }
        // 获取 CreateWorldScreen 实例
        CreateWorldScreen self = (CreateWorldScreen)(Object)this;
        TabNavigationWidget.Builder builder = TabNavigationWidget.builder(this.tabManager, this.width);
        builder.tabs(new NewCreateWorldScreenGameTab(self), self.new WorldTab(), self.new MoreTab());
        this.tabNavigation = builder
                .build();
        this.addDrawableChild(this.tabNavigation);
        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget.add(ButtonWidget.builder(Text.translatable("selectWorld.create"), button -> this.createLevel()).build());
        directionalLayoutWidget.add(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.onCloseScreen()).build());
        this.layout.forEachChild(child -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
        this.tabNavigation.selectTab(0, false);
        this.worldCreator.update();
        this.refreshWidgetPositions();
        ci.cancel();
    }

    @Shadow
    public void onCloseScreen() {}

    @Shadow
    private void createLevel() {}
}

