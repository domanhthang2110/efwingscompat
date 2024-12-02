package com.yukami.efwingscompat;

import com.yukami.efwingscompat.event.FlyingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EFWingsCompat.MODID)
public class EFWingsCompat
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "efwingscompat";
    public EFWingsCompat()
    {
        // Register the FlyingEvent class on the MinecraftForge event bus
        MinecraftForge.EVENT_BUS.register(FlyingEvent.class);
    }
}
