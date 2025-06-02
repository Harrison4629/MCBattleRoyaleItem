package net.harrison.battleroyaleitem.items.rholditem;

import net.harrison.battleroyaleitem.items.AbsRHoldItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MedkitItem extends AbsRHoldItem {
    private static final int USE_DURATION = 45;
    private static final int HEALING_AMOUNT = 12;
    private static final int COOLDOWN_TICKS = 100;

    public MedkitItem(Properties properties) {
        super(properties, USE_DURATION, COOLDOWN_TICKS);
    }

    @Override
    protected void applyItem(Player player, Level level) {
        if (!level.isClientSide) {

            if (player.getHealth() < player.getMaxHealth()) {
                player.heal(HEALING_AMOUNT);

                player.displayClientMessage(Component.translatable("item.battleroyaleitem.medkit.use_success")
                        .withStyle(ChatFormatting.GREEN), true);
            } else {
                player.displayClientMessage(Component.translatable("item.battleroyaleitem.medkit.use_failure")
                        .withStyle(ChatFormatting.YELLOW), true);
            }
        }
    }

    @Override
    protected String getUseTooShortTranslationKey() {
        return "item.battleroyaleitem.medkit.use_short";
    }

    @Override
    protected String getTooltipTranslationKey() {
        return "item.battleroyaleitem.medkit.tooltip";
    }

    @Override
    protected String getUseTooltipTranslationKey() {
        return "item.battleroyaleitem.medkit.tooltip.use";
    }
}
