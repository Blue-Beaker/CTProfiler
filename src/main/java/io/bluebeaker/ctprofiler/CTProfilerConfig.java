package io.bluebeaker.ctprofiler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = CTProfiler.MODID,type = Type.INSTANCE,category = "general")
public class CTProfilerConfig {
    @Comment("Example")
    @LangKey("config.ctprofiler.example.name")
    public static boolean example = true;
}