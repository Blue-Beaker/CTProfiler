package io.bluebeaker.ctprofiler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = CTProfiler.MODID,type = Type.INSTANCE,category = "general")
public class CTProfilerConfig {
    @Comment("Log event times")
    @LangKey("config.ctprofiler.logEventTimes.name")
    public static boolean logEventTimes = false;
}