package com.yukami.efwingscompat.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.paulf.wings.client.renderer.LayerWings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LayerWings.class, remap = false)
public abstract class WingsMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(
            PoseStack matrixStack,
            MultiBufferSource buffer,
            int packedLight,
            Player player,
            float limbSwing,
            float limbSwingAmount,
            float delta,
            float age,
            float headYaw,
            float headPitch,
            CallbackInfo ci
    ) {
        // Check if the camera is in first-person view
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getCameraEntity() == player && minecraft.options.getCameraType().isFirstPerson()) {
            // Cancel the render method
            ci.cancel();
        }
    }
}
