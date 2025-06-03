package net.harrison.battleroyaleitem.networking.c2spacket;

import net.harrison.battleroyaleitem.capabilities.armorplate.NumofArmorPlateProvider;
import net.harrison.battleroyaleitem.init.ModMessages;
import net.harrison.battleroyaleitem.networking.s2cpacket.ArmorPlateSyncS2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ArmorPlateC2SPacket {

    public ArmorPlateC2SPacket(){
    }

    public ArmorPlateC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                player.getCapability(NumofArmorPlateProvider.NUMOF_ARMOR_PLATE_CAPABILITY).ifPresent(numofArmorPlate -> {
                    numofArmorPlate.addArmorPlate(1);
                    ModMessages.sendToPlayer(new ArmorPlateSyncS2CPacket(numofArmorPlate.getNumofArmorPlate()), player);
                });

            }
        });
        context.setPacketHandled(true);
    }
}
