package io.bluebeaker.ctprofiler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CTProfilerLogger {
    static Writer writer;

    static FileHandler handler;

    static {
        try {
            writer = new OutputStreamWriter(Files.newOutputStream(Paths.get("ctprofiler.log")), StandardCharsets.UTF_8);
        } catch (IOException e) {
            CTProfiler.getLogger().error("Error creating ctprofiler.log:",e);
        }
    }

    public static void writeToLog(Object text, Object... args){
        String formatted = String.format(String.valueOf(text), args);
        try {
            writer.write(formatted);
            writer.flush();
        } catch (Throwable e) {
            CTProfiler.getLogger().error("Error logging into ctprofiler.log:",e);
        }
    }
}
