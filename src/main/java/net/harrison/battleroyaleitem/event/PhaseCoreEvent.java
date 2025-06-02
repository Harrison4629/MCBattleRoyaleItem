package net.harrison.battleroyaleitem.event;

import net.harrison.battleroyaleitem.items.rholditem.PhaseCoreItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber
public class PhaseCoreEvent {
    private static final float speed = PhaseCoreItem.PHASE_SPEED;

    @SubscribeEvent
    public static void onServerLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.level.isClientSide && event.level.dimension() == Level.OVERWORLD) {
            if (!PhaseCoreItem.DATA.isEmpty()) {
                for (UUID playerId : PhaseCoreItem.DATA.keySet()) {
                    ServerPlayer player = event.level.getServer().getPlayerList().getPlayer(playerId);
                    if (player != null) {
                        updatePhasing(player);
                    }
                }
            }
        }
    }

    private static void updatePhasing(ServerPlayer player) {


        UUID playerId = player.getUUID();

        double dx = PhaseCoreItem.DATA.get(playerId).readDirection().x()*speed;
        double dy = PhaseCoreItem.DATA.get(playerId).readDirection().y*speed;
        double dz = PhaseCoreItem.DATA.get(playerId).readDirection().z*speed;
        int timeRemaining = PhaseCoreItem.DATA.get(playerId).readRemainingTick();

        if (phaseFinished(playerId)) {
            player.moveTo(PhaseCoreItem.DATA.get(playerId).readOriginPos());
            PhaseCoreItem.DATA.remove(playerId);
        } else {
            player.moveTo(player.getX() + dx, player.getY() + dy, player.getZ() + dz);
            PhaseCoreItem.DATA.get(playerId).modifyRemainingTick(timeRemaining-1);
            System.out.println("[PhaseCore] Player: " + player.getName().getString() +
                    ", GameTime: " + player.level.getGameTime() +
                    ", TicksAfterUpdate: " + PhaseCoreItem.DATA.get(playerId).readRemainingTick());
        }


    }

    private static boolean phaseFinished(UUID playerId) {
        boolean finished = false;
        if (PhaseCoreItem.DATA.get(playerId).readRemainingTick() <=0) {
            finished = true;
        }

        return finished;
    }
}
