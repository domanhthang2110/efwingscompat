package com.yukami.efwingscompat.event;

import com.yukami.efwingscompat.animation.Animation;
import com.yukami.efwingscompat.animation.EFLivingMotions;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.UpdatePlayerMotionEvent;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.client.CPPlayAnimation;


@Mod.EventBusSubscriber(modid = "efwingscompat", value = Dist.CLIENT)
public class FlyingEvent {
    @SubscribeEvent
    public static void onUpdatePlayerMotion(UpdatePlayerMotionEvent event) {
        if (!(event.getPlayerPatch().getOriginal() instanceof LocalPlayer player)){
            return;
        }

        // Server-side handling
        /*if (event.getEntity() instanceof ServerPlayer) {
            Flights.get(player).ifPresent(flight -> {
                ServerPlayerPatch serverPatch = EpicFightCapabilities.getEntityPatch(player, ServerPlayerPatch.class);
                if (serverPatch != null && flight.isFlying()) {
                    serverPatch.currentLivingMotion = EFLivingMotions.WING;
                    serverPatch.playAnimationSynchronized(serverPatch.getAnimator().getLivingAnimation(EFLivingMotions.WING, Animations.DUMMY_ANIMATION), 0.0f);
                    LogUtils.getLogger().info(String.valueOf(serverPatch.getAnimator().getLivingAnimation(LivingMotions.FLY, Animations.DUMMY_ANIMATION)));
                }
            });
        }*/
        // Client-side handling
        if (player instanceof LocalPlayer) {
            Flights.get(player).ifPresent(flight -> {
                //LocalPlayerPatch clientPatch = EpicFightCapabilities.getEntityPatch(player, LocalPlayerPatch.class);
                LocalPlayerPatch clientPatch = (LocalPlayerPatch) event.getPlayerPatch();
                if (clientPatch != null && flight.isFlying()) {
                    clientPatch.currentLivingMotion = EFLivingMotions.WING;
                    //clientPatch.playAnimationSynchronized(Animation.WINGFLY_FORWARD, 0.0f);
                    //clientPatch.getAnimator().playAnimation(Animation.WINGFLY_FORWARD, 0.0f);
                    EpicFightNetworkManager.sendToServer(new CPPlayAnimation(Animation.WINGFLY_FORWARD, 0.0f, true, false));

                }
                //LogUtils.getLogger().info(String.valueOf(clientPatch.getAnimator().getLivingAnimation(clientPatch.getCurrentLivingMotion(), Animations.DUMMY_ANIMATION)));
            });
        }
    }
}