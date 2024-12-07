package com.yukami.efwingscompat.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yukami.efwingscompat.EFWingsCompat;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EFWingsCompat.MODID, value = Dist.CLIENT)
public class RenderHandEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderHand(RenderHandEvent event) {
        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();
        Flights.get(Minecraft.getInstance().player).ifPresent(flight -> {
            if (flight.isFlying()) {
                poseStack.translate(0.0D, -1.2D, 0.2D);
            }
        });
    }
}
