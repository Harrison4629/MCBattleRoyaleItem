package net.harrison.battleroyaleitem.items.rholditem;

import net.harrison.battleroyaleitem.capabilities.armorplate.NumofArmorPlate;
import net.harrison.battleroyaleitem.capabilities.armorplate.NumofArmorPlateProvider;
import net.harrison.battleroyaleitem.init.ModMessages;
import net.harrison.battleroyaleitem.items.AbsRHoldItem;
import net.harrison.battleroyaleitem.networking.c2spacket.ArmorPlateC2SPacket;
import net.harrison.battleroyaleitem.networking.s2cpacket.ArmorPlateSyncS2CPacket;
import net.harrison.battleroyaleitem.particles.ParticleSummon;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;

public class ArmorPlateItem extends AbsRHoldItem {
    private static final int USE_DURATION = 20;
    private static final int COOLDOWN_TICKS = 40;


    public ArmorPlateItem(Properties properties) {
        super(properties, USE_DURATION, COOLDOWN_TICKS);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!level.isClientSide) {
                LazyOptional<NumofArmorPlate> armorCapability = player.getCapability(NumofArmorPlateProvider.NUMOF_ARMOR_PLATE_CAPABILITY);

                armorCapability.ifPresent(numofArmorPlate -> {
                    int armorPlateCount = numofArmorPlate.getNumofArmorPlate();
                    float plateHP = numofArmorPlate.getHP();

                    if (armorPlateCount ==3 && plateHP == NumofArmorPlate.MAX_HP_PER_ARMOR_PLATE) {

                        player.displayClientMessage(Component.translatable("item.battleroyaleitem.armor_plate.use_fail")
                                .withStyle(ChatFormatting.RED), true);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, getVolume(), getPitch());
                    } else {
                        applyItem(player, level);

                        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                getFinishSound(), SoundSource.PLAYERS, getVolume(), getPitch());

                        if (!player.isCreative()) {
                            stack.shrink(1);
                        }

                        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);


                    }

                });
            }

            if (level.isClientSide) {
                spawnParticles(player, level);
            }
        }
        return stack;
    }

    @Override
    protected void applyItem(Player player, Level level) {
        ModMessages.sendToServer(new ArmorPlateC2SPacket());
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
    public SoundEvent getUsingSound() {
        return SoundEvents.STONE_PLACE;
    }

    @Override
    protected SoundEvent getFinishSound() {
        return SoundEvents.ARMOR_EQUIP_GENERIC;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BRUSH;
    }

    @Override
    protected ParticleOptions getParticleType() {
        return ParticleTypes.ELECTRIC_SPARK;
    }
}
