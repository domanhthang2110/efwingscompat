package com.yukami.efwingscompat.mixin;

import com.yukami.efwingscompat.animation.Animation;
import com.yukami.efwingscompat.animation.EFLivingMotions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = PlayerPatch.class, remap = false)
public class PlayerPatchMixin {

    @Inject(
            method = "initAnimator",
            at = @At("TAIL"),
            remap = false
    )
    private void injectCustomAnimations(Animator animator, CallbackInfo ci) {
        animator.addLivingAnimation(EFLivingMotions.WING, Animation.WINGFLY_FORWARD);
    }

        @Inject(method = "isInAir", at = @At("RETURN"), cancellable = true)
        private void addWingFlyingCheck(CallbackInfoReturnable<Boolean> cir) {
            LivingEntityPatch<?> self = (LivingEntityPatch<?>) (Object) this;
            cir.setReturnValue(cir.getReturnValue() || self.currentLivingMotion == EFLivingMotions.WING);
        }
}