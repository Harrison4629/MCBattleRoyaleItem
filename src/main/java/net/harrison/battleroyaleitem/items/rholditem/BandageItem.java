package net.harrison.battleroyaleitem.items.rholditem;

import net.harrison.battleroyaleitem.items.AbsRHoldItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BandageItem extends AbsRHoldItem {
    private static final int USE_DURATION = 20;
    private static final int HEALING_AMOUNT = 4;
    private static final int COOLDOWN_TICKS = 40;

    public BandageItem(Properties properties) {
        super(properties, USE_DURATION, COOLDOWN_TICKS);
    }

    @Override
    protected void applyItem(Player player, Level level) {

        player.heal(HEALING_AMOUNT);

        player.displayClientMessage(Component.translatable("item.battleroyaleitem.bandage.use_success")
                .withStyle(ChatFormatting.GREEN), true);
    }

    @Override
    protected String getFailTranslationKey() {
        return "";
    }

    @Override
    protected String getTooltipTranslationKey() {
        return "";
    }

    @Override
    protected String getUseTooltipTranslationKey() {
        return "";
    }

}
