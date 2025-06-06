package net.harrison.battleroyaleitem;

import net.harrison.battleroyaleitem.init.*;
import net.harrison.battleroyaleitem.client.screens.ArmorPlateHudOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod(Battleroyaleitem.MODID)
public class Battleroyaleitem {

    public static final String MODID = "battleroyaleitem";
    public Battleroyaleitem() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        ModItems.ITEMS.register(modEventBus);
        ModEntities.register(modEventBus);



        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(ModMessages::register);
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == ModCreativeModeTab.BATTLEROYALEITEMMOD) {
            event.accept(ModItems.MEDKIT.get());
            event.accept(ModItems.BANDAGE.get());
            event.accept(ModItems.CHAMELEON.get());
            event.accept(ModItems.REGENERATION_SYRINGE.get());
            event.accept(ModItems.PHASE_CORE.get());
            event.accept(ModItems.BIO_RADAR.get());
            event.accept(ModItems.LIFT_DEVICE.get());
            event.accept(ModItems.ARMOR_PLATE.get());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(ModKeyBinds.STOP_PHASING);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("armor_plate", ArmorPlateHudOverlay.HUD_ARMOR_PLATE);
        }



        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }


}
