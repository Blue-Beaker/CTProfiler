package io.bluebeaker.ctprofiler;

public class ProfilerCache {
    public final long time;
    public final String loader;
    public final String scriptFile;

    public ProfilerCache(long time, String loader, String scriptFile) {
        this.time = time;
        this.loader = loader;
        this.scriptFile = scriptFile;
    }
}
