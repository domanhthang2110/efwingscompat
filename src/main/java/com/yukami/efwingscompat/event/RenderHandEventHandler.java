package com.yukami.efwingscompat.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.yukami.efwingscompat.EFWingsCompat;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

@Mod.EventBusSubscriber(modid = EFWingsCompat.MODID, value = Dist.CLIENT)
public class RenderHandEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderHand(RenderHandEvent event) {
        PoseStack poseStack = event.getPoseStack();
        LocalPlayer player = Minecraft.getInstance().player;
        Flights.get(player).ifPresent(flight -> {
            if (flight.isFlying()) {
                assert player != null;
                float cameraXRot = Minecraft.getInstance().gameRenderer.getMainCamera().getXRot();
                float targetRotation = 0;

                Matrix4f transform = new Matrix4f();
                if (player.isUsingItem() && player.getUseItem().getUseAnimation() != UseAnim.EAT
                        && player.getUseItem().getUseAnimation() != UseAnim.DRINK) {
                    transform.translate(0.0F, -0.5F, 0F);
                } else {
                    targetRotation = (float) (Math.toRadians(Math.min(Math.max(cameraXRot, -70), 90)) + Math.toRadians(10.0f));
                    transform.translate(0.0F, -0.3F, 0F);
                }

                transform.rotateX(targetRotation);
                transform.setTranslation(0.0f, transform.getTranslation(new Vector3f()).y, 0.0f);
                poseStack.last().pose().set(transform);
            }
        });
    }
}