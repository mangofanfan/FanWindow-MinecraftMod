package cn.mangofanfan.fanwindow.client;

import net.minecraft.client.network.ServerInfo;

public class GlobalState {
    private static GlobalState instance;
    private boolean started = false;
    private boolean newMainWindowInUse = true;
    private boolean modMenuSupport = false;
    private boolean replayModSupport = false;

    /**
     * 最近尝试连接的服务器的 {@link ServerInfo}，用于快捷重新连接。
     */
    private ServerInfo lastServerEntry = null;

    private GlobalState() {} // 私有构造

    public static synchronized GlobalState getInstance() {
        if (instance == null) {
            instance = new GlobalState();
        }
        return instance;
    }

    public synchronized boolean isStarted() {
        return started;
    }

    public synchronized void setStarted(boolean value) {
        this.started = value;
    }

    public synchronized boolean isNewMainWindowInUse() {
        return newMainWindowInUse;
    }

    public synchronized void setNewMainWindowInUse(boolean value) {
        this.newMainWindowInUse = value;
    }

    public boolean isModMenuSupport() {
        return modMenuSupport;
    }

    public void setModMenuSupport(boolean value) {
        this.modMenuSupport = value;
    }

    public ServerInfo getLastServerEntry() {
        return lastServerEntry;
    }

    public void setLastServerEntry(ServerInfo lastServerEntry) {
        this.lastServerEntry = lastServerEntry;
    }

    public boolean isReplayModSupport() {
        return replayModSupport;
    }

    public void setReplayModSupport(boolean replayModSupport) {
        this.replayModSupport = replayModSupport;
    }
}