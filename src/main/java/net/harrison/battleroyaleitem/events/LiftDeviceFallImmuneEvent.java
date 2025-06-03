package net.harrison.battleroyaleitem.events;

import net.harrison.battleroyaleitem.Battleroyaleitem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Battleroyaleitem.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LiftDeviceFallImmuneEvent {
    private static final Map<UUID, Boolean> FALL_IMMUNE = new HashMap<>();

    public static boolean isImmune(UUID playerId) {
        return FALL_IMMUNE.getOrDefault(playerId, false);
    }

    public static void setImmune(UUID playerId, boolean value) {
        FALL_IMMUNE.put(playerId, value);
    }

    public static void resetImmune(UUID playerId) {
        FALL_IMMUNE.remove(playerId);
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (source.is(DamageTypes.FALL) && !event.getEntity().level.isClientSide() && event.getEntity() instanceof Player player) {
            if (isImmune(player.getUUID())) {
                event.setCanceled(true);
                resetImmune(player.getUUID());
            }
        }
    }
}
