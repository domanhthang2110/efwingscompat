package com.yukami.efwingscompat.animation;

import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.gameasset.Armatures;

public class Animation {
    public static AnimationAccessor<StaticAnimation> WINGFLY_FORWARD;

    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder("efwingscompat", Animation::build);
    }

    public static void build(AnimationManager.AnimationBuilder builder) {
        // ========================================
        // WINGS FLYING ANIMATIONS
        // ========================================
        WINGFLY_FORWARD = builder.nextAccessor("biped/living/wing", (accessor) -> new StaticAnimation(true, accessor, Armatures.BIPED));
    }
}