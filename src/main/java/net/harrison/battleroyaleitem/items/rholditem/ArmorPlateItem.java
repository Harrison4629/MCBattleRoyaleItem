package net.harrison.battleroyaleitem.items.rholditem;

import net.harrison.battleroyaleitem.capabilities.armorplate.NumofArmorPlate;
import net.harrison.battleroyaleitem.capabilities.armorplate.NumofArmorPlateProvider;
import net.harrison.battleroyaleitem.init.ModMessages;
import net.harrison.battleroyaleitem.items.AbsRHoldItem;
import net.harrison.battleroyaleitem.networking.s2cpacket.ArmorPlateSyncS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;

import java.util.concurrent.atomic.AtomicBoolean;

public class ArmorPlateItem extends AbsRHoldItem {
    private static final int USE_DURATION = 30;
    private static final int COOLDOWN_TICKS = 10;


    public ArmorPlateItem(Properties properties) {
        super(properties, USE_DURATION, COOLDOWN_TICKS);
    }

    @Override
    protected boolean conditionsMet(Player player, Level level) {
        AtomicBoolean met = new AtomicBoolean(true);
        LazyOptional<NumofArmorPlate> armorCapability = player.getCapability(NumofArmorPlateProvider.NUMOF_ARMOR_PLATE_CAPABILITY);
        armorCapability.ifPresent(numofArmorPlate -> {
            int armorPlateCount = numofArmorPlate.getNumofArmorPlate();
            float plateHP = numofArmorPlate.getHP();
            met.set(armorPlateCount < NumofArmorPlate.MAX_ARMOR_PLATE || plateHP < NumofArmorPlate.MAX_HP_PER_ARMOR_PLATE);
        });
        return met.get();
    }

    @Override
    protected void applyItem(Player player, Level level) {
        player.getCapability(NumofArmorPlateProvider.NUMOF_ARMOR_PLATE_CAPABILITY).ifPresent(numofArmorPlate -> {
            numofArmorPlate.addArmorPlate(1);
            ModMessages.sendToPlayer(new ArmorPlateSyncS2CPacket(numofArmorPlate.getNumofArmorPlate()), (ServerPlayer) player);
        });
    }

    @Override
    protected String getUseTooShortTranslationKey() {
        return "item.battleroyaleitem.armor_plate.use_short";
    }

    @Override
    protected String getTooltipTranslationKey() {
        return "item.battleroyaleitem.armor_plate.tooltip";
    }

    @Override
    protected String getUseTooltipTranslationKey() {
        return "item.battleroyaleitem.armor_plate.tooltip.use";
    }

    @Override
    protected String getUseFailTranslationKey() {
        return "item.battleroyaleitem.armor_plate.use_fail";
    }

    @Override
    public SoundEvent getUsingSound() {
        return SoundEvents.STONE_PLACE;
    }

    @Override
    protected SoundEvent getFinishSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BRUSH;
    }

    @Override
    protected ParticleOptions getParticleType() {
        return ParticleTypes.ELECTRIC_SPARK;
    }

    @Override
    public float getVolume() {
        return 1.5F;
    }
}
