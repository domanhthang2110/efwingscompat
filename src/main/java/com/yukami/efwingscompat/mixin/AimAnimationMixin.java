package com.yukami.efwingscompat.mixin;

import com.yukami.efwingscompat.animation.EFLivingMotions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.AimAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = AimAnimation.class, remap = false)
public class AimAnimationMixin {
    @Inject(method = "getPoseByTime", at = @At("HEAD"), cancellable = true)
    private void onGetPoseByTime(LivingEntityPatch<?> entitypatch, float time, float partialTicks, CallbackInfoReturnable<Pose> cir) {
        if (!entitypatch.isFirstPerson()) {
            LivingMotion livingMotion = entitypatch.getCurrentLivingMotion();

            if (livingMotion == EFLivingMotions.WING) {
                AimAnimation self = (AimAnimation)(Object)this;
                Pose pose = self.lying.getPoseByTime(entitypatch, time, partialTicks);
                self.modifyPose(self, pose, entitypatch, time, partialTicks);

                cir.setReturnValue(pose);
            }
        }
    }
}
