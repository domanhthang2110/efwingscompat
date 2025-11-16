package com.yukami.efwingscompat.player;

import com.yukami.efwingscompat.EFWingsCompat;
import com.yukami.efwingscompat.animation.EFLivingMotions;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.UpdatePlayerMotionEvent;

/**
 * Handles wing living motion using Epic Fight's UpdatePlayerMotionEvent
 */
@Mod.EventBusSubscriber(modid = EFWingsCompat.MODID, value = Dist.CLIENT)
public class WingMotionHandler {

    @SubscribeEvent(priority = net.minecraftforge.eventbus.api.EventPriority.HIGH)
    public static void onUpdatePlayerMotion(UpdatePlayerMotionEvent.BaseLayer event) {
        Player player = event.getPlayerPatch().getOriginal();
                Flights.get(player).ifPresent(flight -> {
            if (flight.isFlying() && event.getPlayerPatch().isEpicFightMode()) {
                event.setMotion(EFLivingMotions.WING);
            }
        });
    }
}
