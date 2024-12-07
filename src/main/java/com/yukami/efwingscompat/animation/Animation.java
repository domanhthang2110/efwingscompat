package com.yukami.efwingscompat.animation;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.QuaternionUtils;
import yesman.epicfight.config.EpicFightOptions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

public class Animation {
    public static StaticAnimation WINGFLY_FORWARD;

    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put("efwingscompat", Animation::Build);
    }

    protected static void Build(){
        HumanoidArmature biped = Armatures.BIPED;
        //One-handed chanting
        WINGFLY_FORWARD =  new MovementAnimation(EpicFightOptions.GENERAL_ANIMATION_CONVERT_TIME, true, "biped/living/wingfly_forward", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, Animation.FLYING_CORRECTION);
    }

    public static final AnimationProperty.PoseModifier FLYING_CORRECTION = (self, pose, entitypatch, elapsedTime, partialTicks) -> {
        Vec3 vec3d = ((LivingEntity)entitypatch.getOriginal()).getViewVector(partialTicks);
        Vec3 vec3d1 = ((LivingEntity)entitypatch.getOriginal()).getDeltaMovement();
        double d0 = vec3d1.horizontalDistanceSqr();
        double d1 = vec3d.horizontalDistanceSqr();
        if (d0 > (double)0.0F && d1 > (double)0.0F) {
            JointTransform root = pose.getOrDefaultTransform("Root");
            JointTransform head = pose.getOrDefaultTransform("Head");
            double d2 = (vec3d1.x * vec3d.x + vec3d1.z * vec3d.z) / (Math.sqrt(d0) * Math.sqrt(d1));
            double d3 = vec3d1.x * vec3d.z - vec3d1.z * vec3d.x;
            float zRot = Mth.clamp((float)(Math.signum(d3) * Math.acos(d2)), -1.0F, 1.0F);
            root.frontResult(JointTransform.getRotation(QuaternionUtils.ZP.rotation(zRot)), OpenMatrix4f::mulAsOriginInverse);
            float xRot = (float) MathUtils.getXRotOfVector(vec3d1) * 0.8F;
            MathUtils.mulQuaternion(QuaternionUtils.XP.rotationDegrees(xRot), root.rotation(), root.rotation());
            MathUtils.mulQuaternion(QuaternionUtils.XP.rotationDegrees(-xRot), head.rotation(), head.rotation());
        }

    };
}
