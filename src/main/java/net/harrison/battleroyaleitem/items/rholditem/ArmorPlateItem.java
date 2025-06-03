package net.harrison.battleroyaleitem.items.rholditem;

import net.harrison.battleroyaleitem.init.ModMessages;
import net.harrison.battleroyaleitem.items.AbsRHoldItem;
import net.harrison.battleroyaleitem.networking.c2spacket.ArmorPlateC2SPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ArmorPlateItem extends AbsRHoldItem {
    private static final int USE_DURATION = 20;
    private static final int COOLDOWN_TICKS = 40;


    public ArmorPlateItem(Properties properties) {
        super(properties, USE_DURATION, COOLDOWN_TICKS);
    }

    @Override
    protected void applyItem(Player player, Level level) {
        ModMessages.sendToServer(new ArmorPlateC2SPacket());
    }

    @Override
    protected String getUseTooShortTranslationKey() {
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
