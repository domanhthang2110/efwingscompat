package com.yukami.efwingscompat.mixin;

import com.yukami.efwingscompat.animation.Animation;
import com.yukami.efwingscompat.animation.EFLivingMotions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

/**
 * Mixin to register wing living animation exactly like Epic Fight does for FALL
 * This follows Epic Fight's own pattern in PlayerPatch.initAnimator()
 */
@Mixin(PlayerPatch.class)
public class PlayerPatchMixin {

    @Inject(method = "initAnimator", at = @At("TAIL"), remap = false)
    private void addWingLivingAnimation(Animator animator, CallbackInfo ci) {
        animator.addLivingAnimation(EFLivingMotions.WING, Animation.WINGFLY_FORWARD);
    }
}