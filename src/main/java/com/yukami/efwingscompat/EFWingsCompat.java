package com.yukami.efwingscompat;

import com.yukami.efwingscompat.animation.Animation;
import com.yukami.efwingscompat.animation.EFLivingMotions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yesman.epicfight.api.animation.LivingMotion;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EFWingsCompat.MODID)
public class EFWingsCompat
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "efwingscompat";
    public EFWingsCompat()
    {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus bus = context.getModEventBus();
        bus.addListener(Animation::registerAnimations);
        LivingMotion.ENUM_MANAGER.registerEnumCls(EFWingsCompat.MODID, EFLivingMotions.class);
    }
}
