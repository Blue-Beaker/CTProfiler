package io.bluebeaker.ctprofiler.mixin;

import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import io.bluebeaker.ctprofiler.CTProfilerConfig;
import io.bluebeaker.ctprofiler.EventProfiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EventList.class,remap = false)
public abstract class MixinEventList<T> {
    @Redirect(method = "publish",at= @At(value = "INVOKE", target = "Lcrafttweaker/util/IEventHandler;handle(Ljava/lang/Object;)V"))
    public void profileEventHandler(IEventHandler<T> instance, T t){
        if(!CTProfilerConfig.logEventTimes) {
            instance.handle(t);
            return;
        }
        long time = System.nanoTime();
        instance.handle(t);
        long timeCost = System.nanoTime() - time;
        EventProfiler.addTime(instance,timeCost);
    }
}
