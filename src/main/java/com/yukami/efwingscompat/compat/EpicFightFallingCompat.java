package com.yukami.efwingscompat.compat;

import com.yukami.efwingscompat.EFWingsCompat;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.forgeevent.RenderEpicFightPlayerEvent;

/**
 * Compatibility to disable Epic Fight's falling animation when player is flying with wings
 * This prevents the falling arm animation from playing during any wing flight
 */
@Mod.EventBusSubscriber(modid = EFWingsCompat.MODID, value = Dist.CLIENT)
public class EpicFightFallingCompat {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderEpicFightPlayer(RenderEpicFightPlayerEvent event) {
        Player player = event.getPlayerPatch().getOriginal();
        
        // Check if player is flying with wings
        Flights.get(player).ifPresent(flight -> {
            if (flight.isFlying()) {
                boolean isFallingMotion = event.getPlayerPatch().getCurrentLivingMotion() == LivingMotions.FALL;
                
                if (isFallingMotion) {
                    event.setShouldRender(false);
                }
            }
        });
    }
}