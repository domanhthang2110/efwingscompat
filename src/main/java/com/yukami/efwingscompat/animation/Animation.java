package com.yukami.efwingscompat.animation;

import com.mojang.serialization.Codec;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
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
        AbstractClientPlayer player = (AbstractClientPlayer) entitypatch.getOriginal();
        Vec3 viewVector = player.getViewVector(partialTicks);
        Vec3 moveVector = player.getDeltaMovementLerped(partialTicks);

        JointTransform root = pose.getOrDefaultTransform("Root");
        JointTransform head = pose.getOrDefaultTransform("Head");

        Vector3f currentRotations = new Vector3f();
        root.rotation().getEulerAnglesXYZ(currentRotations);
            double horizontalMove = moveVector.horizontalDistanceSqr();
            double horizontalView = viewVector.horizontalDistanceSqr();
            float pitchAngle = (float)MathUtils.getXRotOfVector(viewVector) * 1.2F;
            pitchAngle = Mth.clamp(pitchAngle, -85f, 85f);
            // Add a vertical movement factor
            float verticalFactor = 1.0F - Math.abs(pitchAngle) / 85f;
            // Apply the roll rotation with vertical factor
            if (horizontalMove > 0.0F && horizontalView > 0.0F) {
                double d2 = (moveVector.x * viewVector.x + moveVector.z * viewVector.z) / (Math.sqrt(horizontalMove) * Math.sqrt(horizontalView));
                double d3 = moveVector.x * viewVector.z - moveVector.z * viewVector.x;
            float zRot = Mth.clamp((float)(Math.signum(d3) * Math.acos(d2)), -1.0F, 1.0F);
                // Multiply roll by verticalFactor to reduce its influence during vertical movement
                root.frontResult(JointTransform.getRotation(QuaternionUtils.ZP.rotation(zRot * verticalFactor)), OpenMatrix4f::mulAsOriginInverse);
            }
            // Apply pitch after roll
            MathUtils.mulQuaternion(QuaternionUtils.XP.rotationDegrees(pitchAngle), root.rotation(), root.rotation());
            MathUtils.mulQuaternion(QuaternionUtils.XP.rotationDegrees(-pitchAngle), head.rotation(), head.rotation());
    };

}