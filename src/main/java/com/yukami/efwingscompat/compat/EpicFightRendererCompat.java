package com.yukami.efwingscompat.compat;

import me.paulf.wings.server.flight.Flights;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.RenderEpicFightPlayerEvent;

/**
 * Compatibility with Epic Fight to disable their rendering when player is flying with wings
 * Following the same pattern as Epic Fight's PlayerAnimatorCompat
 */
@Mod.EventBusSubscriber(modid = "efwingscompat", value = Dist.CLIENT)
public class EpicFightRendererCompat {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderEpicFightPlayer(RenderEpicFightPlayerEvent event) {
        Player player = event.getPlayerPatch().getOriginal();
        
        Flights.get(player).ifPresent(flight -> {
            if (flight.isFlying()) {
                event.setShouldRender(false);
            }
        });
    }
}