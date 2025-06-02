package net.harrison.battleroyaleitem.init;

import net.harrison.battleroyaleitem.Battleroyaleitem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Battleroyaleitem.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTab {
    public static CreativeModeTab BATTLEROYALEITEMMOD;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        BATTLEROYALEITEMMOD = event.registerCreativeModeTab(ResourceLocation.fromNamespaceAndPath(Battleroyaleitem.MODID, "battleroyaleitemtab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.MEDKIT.get())).title(Component.translatable("itemGroup.battleroyaleitemtab")).build());
    }
}
