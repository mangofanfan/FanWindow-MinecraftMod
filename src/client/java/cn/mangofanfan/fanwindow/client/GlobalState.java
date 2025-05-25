package cn.mangofanfan.fanwindow.client;

public class GlobalState {
    private static GlobalState instance;
    private boolean started = false;
    private boolean newMainWindowInUse = true;
    private boolean modMenuSupport = false;

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
}