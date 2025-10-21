package io.bluebeaker.ctprofiler.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import crafttweaker.runtime.CrTTweaker;
import crafttweaker.runtime.ScriptFile;
import crafttweaker.runtime.ScriptLoader;
import io.bluebeaker.ctprofiler.CTProfiler;
import io.bluebeaker.ctprofiler.ProfilerCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = CrTTweaker.class,remap = false)
public abstract class MixinCrTTweaker {
    private static final Logger log = LogManager.getLogger(MixinCrTTweaker.class);

    @Redirect(method = "loadScript(ZLcrafttweaker/runtime/ScriptLoader;Ljava/util/List;Z)Z",at= @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V"))
    public void runScriptWrapped(Runnable instance, @Local(argsOnly = true) ScriptLoader loader, @Local ScriptFile scriptFile){
        long time = System.currentTimeMillis();
        instance.run();
        long timeCost = System.currentTimeMillis() - time;
        CTProfiler.profilerCaches.add(new ProfilerCache(timeCost,loader.toString(),scriptFile.toString()));
    }
}
