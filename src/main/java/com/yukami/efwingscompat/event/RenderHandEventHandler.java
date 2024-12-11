package com.yukami.efwingscompat.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.yukami.efwingscompat.EFWingsCompat;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BowItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.logging.Logger;

@Mod.EventBusSubscriber(modid = EFWingsCompat.MODID, value = Dist.CLIENT)
public class RenderHandEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderHand(RenderHandEvent event) {
        PoseStack poseStack = event.getPoseStack();
        LocalPlayer player = Minecraft.getInstance().player;

        Flights.get(player).ifPresent(flight -> {
            if (flight.isFlying()) {
                // Clone current transformation
                Matrix4f transform = new Matrix4f(poseStack.last().pose());



                assert player != null;

                // Get the camera's pitch (X rotation)
                float cameraXRot = Minecraft.getInstance().gameRenderer.getMainCamera().getXRot(); // Camera pitch (view angle)

                // Calculate the desired rotation based on camera's X rotation (pitch)
                float targetRotation = 0;
                if (player.isUsingItem() && player.getUseItem().getItem() instanceof BowItem) {
                    // When using a BowItem, apply different translation
                    transform.translate(0.0F, -0.5F, 0F);
                } else {
                    // When not using a BowItem, calculate rotation based on camera angle
                    targetRotation = (float) Math.toRadians(Math.min(Math.max(cameraXRot, -45), 70));
                    transform.translate(0.0F, -0.4F, 0F);
                }
                // Apply the rotation directly without smoothing
                transform.rotateX((float) (targetRotation * 1.05));
                LogUtils.getLogger().info("Cam angle {}", cameraXRot);
                LogUtils.getLogger().info("Target rot {}", Math.toDegrees(targetRotation));
                poseStack.last().pose().set(transform);  // Apply the transformation immediately
            }
        });
    }
}
