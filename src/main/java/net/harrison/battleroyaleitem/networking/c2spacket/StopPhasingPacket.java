package net.harrison.battleroyaleitem.networking.c2spacket;

import net.harrison.battleroyaleitem.capabilities.temporary.PhaseData;
import net.harrison.battleroyaleitem.items.rholditem.PhaseCoreItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class StopPhasingPacket {
    public static final Map<UUID, Boolean> KEY_PRESSED_MAP = new HashMap<>();

    public StopPhasingPacket() {
    }

    public StopPhasingPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public static boolean isKeyPressed(UUID playerId) {
        return KEY_PRESSED_MAP.getOrDefault(playerId, false);
    }

    public static void resetKeyPressed(UUID playerId) {
        KEY_PRESSED_MAP.remove(playerId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // 获取发送数据包的玩家
            ServerPlayer player = context.getSender();
            if (player != null) {
                UUID playerId = player.getUUID();
                // 仅当玩家正在位移时记录按键状态
                if (PhaseData.DATA.get(playerId) != null) {
                    KEY_PRESSED_MAP.put(playerId, true);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
