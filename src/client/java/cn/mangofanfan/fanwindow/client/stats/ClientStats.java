package cn.mangofanfan.fanwindow.client.stats;

import net.minecraft.client.network.ServerInfo;

public class ClientStats {
    static ClientStats instance = new ClientStats();

    private ServerInfo lastServerEntry;

    private ClientStats(){
        lastServerEntry = null;
    }

    public static void setLastServerEntry(ServerInfo lastServerEntry) {
        instance.lastServerEntry = lastServerEntry;
    }

    public static ServerInfo getLastServerEntry() {
        return instance.lastServerEntry;
    }
}
