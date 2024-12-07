package com.yukami.efwingscompat.animation;

import yesman.epicfight.api.animation.LivingMotion;

public enum EFLivingMotions implements LivingMotion {
    WING;

    final int id;

    EFLivingMotions() {
        this.id = LivingMotion.ENUM_MANAGER.assign(this);
    }

    public int universalOrdinal() {
        return this.id;
    }
}
