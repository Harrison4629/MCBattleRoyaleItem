package net.harrison.battleroyaleitem.items.rholditem.bioRadar;

import net.harrison.battleroyaleitem.items.AbsRHoldItem;
import net.harrison.battleroyaleitem.particles.ParticleSummon;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class BioRadarItem extends AbsRHoldItem implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public static final int USE_DURATION = 60;
    public static final int COOLDOWN_TICKS = 200;
    public static final int RANGE = 80;
    public static final int GLOW_DURATION = 150;

    public BioRadarItem(Properties properties) {
        super(properties, USE_DURATION, COOLDOWN_TICKS);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!level.isClientSide) {

                if (player.getHealth() > 6.0f) {
                    applyItem(player, level);

                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            getFinishSound(), SoundSource.PLAYERS, getVolume(), getPitch());

                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }

                } else {
                    player.displayClientMessage(Component.translatable("item.battleroyaleitem.bio_radar.use_fail")
                            .withStyle(ChatFormatting.RED), true);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, getVolume(), getPitch());
                }

                player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
            }

            if (level.isClientSide) {
                spawnParticles(player, level);
            }
        }
        return stack;
    }

    @Override
    protected void applyItem(Player player, Level level) {

        AtomicInteger scanned = new AtomicInteger(0);
        player.hurt(level.damageSources().generic(), 6);

        AABB searchArea = new AABB(
                player.getX() - RANGE, player.getY() - RANGE, player.getZ() - RANGE,
                player.getX() + RANGE, player.getY() + RANGE, player.getZ() + RANGE
        );

        level.getEntitiesOfClass(Player.class, searchArea, otherPlayer -> {
            if (otherPlayer == player) return false;

            double distance = player.position().vectorTo(otherPlayer.position()).length();
            if(distance<=RANGE) {
                otherPlayer.addEffect(new MobEffectInstance(MobEffects.GLOWING, GLOW_DURATION, 0, false, false));
                ParticleSummon.spawnClientParticles(otherPlayer.getPosition(1.0F), ParticleTypes.ELDER_GUARDIAN, 1);
                otherPlayer.playSound(SoundEvents.ELDER_GUARDIAN_CURSE);
                otherPlayer.displayClientMessage(Component.translatable("item.battleroyaleitem.bio_radar.detected")
                        .withStyle(ChatFormatting.RED), true);
                scanned.incrementAndGet();
            }
            return false;
        });
        player.displayClientMessage(Component.translatable("item.battleroyaleitem.bio_radar.use_success", scanned.get())
                .withStyle(ChatFormatting.YELLOW), true);
    }

    @Override
    protected ParticleOptions getParticleType() {
        return ParticleTypes.SOUL;
    }

    @Override
    protected SoundEvent getFinishSound() {
        return SoundEvents.ENCHANTMENT_TABLE_USE;
    }

    @Override
    public SoundEvent getUsingSound() {
        return SoundEvents.BEACON_AMBIENT;
    }

    @Override
    protected String getUseTooShortTranslationKey() {
        return "item.battleroyaleitem.bio_radar.use_short";
    }

    @Override
    protected String getTooltipTranslationKey() {
        return "item.battleroyaleitem.bio_radar.tooltip";
    }

    @Override
    protected String getUseTooltipTranslationKey() {
        return "item.battleroyaleitem.bio_radar.tooltip.use";
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("animation.bio_radar.detect",
                Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private BioRadarItemRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    renderer = new BioRadarItemRenderer();
                }
                return this.renderer;
            }
        });
    }
}
