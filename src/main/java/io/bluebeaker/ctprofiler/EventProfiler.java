package io.bluebeaker.ctprofiler;

import crafttweaker.util.IEventHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventProfiler {
    protected static Map<IEventHandler<?>,Record> times = new HashMap<>();

    public static void addTime(IEventHandler<?> eventHandler, long time){
        if(!CTProfilerConfig.logEventTimes) return;
        if(!times.containsKey(eventHandler)){
            times.put(eventHandler,new Record(time));
        }else {
            times.get(eventHandler).addTime(time);
        }
    }

    public static class Record{
        public long timeNs;
        public int count;
        public Record(long timeNs) {
            this.timeNs = timeNs;
            this.count = 1;
        }
        public void addTime(long time){
            this.timeNs +=time;
            this.count+=1;
        }
    }

    public static Map<IEventHandler<?>,Record> dump(){
        Map<IEventHandler<?>, Record> records = Collections.unmodifiableMap(new HashMap<>(times));
        clearLogs();
        return records;
    }

    public static void clearLogs(){
        times.clear();
    }
}
