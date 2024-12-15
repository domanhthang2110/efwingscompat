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
import org.joml.Vector3f;

import java.util.logging.Logger;

@Mod.EventBusSubscriber(modid = EFWingsCompat.MODID, value = Dist.CLIENT)
public class RenderHandEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderHand(RenderHandEvent event) {
        PoseStack poseStack = event.getPoseStack();
        LocalPlayer player = Minecraft.getInstance().player;
        Flights.get(player).ifPresent(flight -> {
            if (flight.isFlying()) {
                // Store the existing transformation
                Matrix4f existingTransform = new Matrix4f(poseStack.last().pose());

                // Create our new transformation
                Matrix4f ourTransform = new Matrix4f(existingTransform);
                assert player != null;
                float cameraXRot = Minecraft.getInstance().gameRenderer.getMainCamera().getXRot();
                float targetRotation = 0;

                if (player.isUsingItem() && player.getUseItem().getItem() instanceof BowItem) {
                    ourTransform.translate(0.0F, -0.5F, 0F);
                } else {
                    targetRotation = (float) (Math.toRadians(Math.min(Math.max(cameraXRot, -70), 90)) + Math.toRadians(10.0f));
                    ourTransform.translate(0.0F, -0.3F, 0F);
                }

                ourTransform.rotateX(targetRotation);
                ourTransform.setTranslation(0.0f, ourTransform.getTranslation(new Vector3f()).y, 0.0f);

                // Combine transformations
                existingTransform.mul(ourTransform);

                // Apply combined transformation
                poseStack.last().pose().set(existingTransform);
            }
        });
    }
}
