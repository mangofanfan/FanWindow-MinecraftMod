package cn.mangofanfan.fanwindow.client.function;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;

public class SimpleToastBuilder {
    MinecraftClient client;

    public SimpleToastBuilder() {
        client =  MinecraftClient.getInstance();
    }

    public void show(Text title, Text description) {
        SystemToast.show(client.getToastManager(), SystemToast.Type.UNSECURE_SERVER_WARNING, title, description);
    }
}
