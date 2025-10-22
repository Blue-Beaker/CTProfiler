package io.bluebeaker.ctprofiler;

import crafttweaker.util.IEventHandler;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Logger;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class CTProfiler
{
    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
    
    public MinecraftServer server;

    private static Logger logger;

    public static List<ProfilerCache> profilerCaches = new ArrayList<>();
    
    public CTProfiler() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }
    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        this.server=event.getServer();
    }


    @EventHandler
    public void onStarted(FMLLoadCompleteEvent event) {
        profilerCaches.sort((e,i)-> Long.compare(i.time,e.time));
        StringBuilder builder = new StringBuilder("Script load times: \nTime\t\tLoader\t\tFile");
        for (ProfilerCache profilerCache : profilerCaches) {
            builder.append("\n").append(profilerCache.time).append("ms\t\t").append(profilerCache.loader).append("\t\t").append(profilerCache.scriptFile);
        }
        CTProfilerLogger.writeToLog(builder);
    }

    int tickCount = 0;

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event){
        tickCount++;
        if(tickCount>=200){
            tickCount=0;
            StringBuilder builder = new StringBuilder("Event execution times: \nTime\t\tCount\t\tEventHandler");
            Map<IEventHandler<?>, EventProfiler.Record> dump = EventProfiler.dump();
            if(dump.isEmpty()) return;
            for (Map.Entry<IEventHandler<?>, EventProfiler.Record> entry : dump.entrySet()) {
                builder.append("\n").append(String.format("%.3f",(float) entry.getValue().timeNs /1000000)).append("ms\t\t").append(entry.getValue().count).append("\t\t").append(entry.getKey());
            }
            CTProfilerLogger.writeToLog(builder);
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
        }
    }

    public static Logger getLogger(){
        return logger;
    }
}
