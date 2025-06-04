package net.harrison.battleroyaleitem.events;

import net.harrison.battleroyalezone.Battleroyalezone;
import net.harrison.battleroyalezone.events.customEvents.ZoneStageEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = Battleroyalezone.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ZoneEvent {
    @SubscribeEvent
    public static void onZoneEvent(ZoneStageEvent event) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        server.getPlayerList().broadcastSystemMessage(Component.nullToEmpty("目前阶段" + event.getStage() + "目前状态" + event.getState()), false);
    }
}
