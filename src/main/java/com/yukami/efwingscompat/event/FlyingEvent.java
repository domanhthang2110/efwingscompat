package com.yukami.efwingscompat.event;

import com.mojang.logging.LogUtils;
import me.paulf.wings.server.asm.PlayerFlightCheckEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.forgeevent.UpdatePlayerMotionEvent;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

@Mod.EventBusSubscriber(modid = "efwingscompat", value = Dist.CLIENT)
public class FlyingEvent {

    @SubscribeEvent
    public static void onUpdatePlayerMotion(PlayerFlightCheckEvent event) {
        AbstractClientPlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(Minecraft.getInstance().player, AbstractClientPlayerPatch.class);

        // Check your custom condition
        if (event.isFlying()) {
            // Override the LivingMotion
            LogUtils.getLogger().info("Flyinggggg");
            assert playerPatch != null;
            playerPatch.currentLivingMotion = LivingMotions.FLY;

        }
    }
}