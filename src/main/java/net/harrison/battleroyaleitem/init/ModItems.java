package net.harrison.battleroyaleitem.init;

import net.harrison.battleroyaleitem.Battleroyaleitem;
import net.harrison.battleroyaleitem.items.rholditem.BandageItem;
import net.harrison.battleroyaleitem.items.rholditem.ChameleonItem;
import net.harrison.battleroyaleitem.items.rholditem.MedkitItem;
import net.harrison.battleroyaleitem.items.rholditem.RegenerationSyringItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Battleroyaleitem.MODID);

    public static final RegistryObject<Item> MEDKIT = ITEMS.register("medkit",
            () -> new MedkitItem(new Item.Properties().stacksTo(5)));
    public static final RegistryObject<Item> BANDAGE = ITEMS.register("bandage",
            () -> new BandageItem(new Item.Properties().stacksTo(8)));
    public static final RegistryObject<Item> CHAMELEON = ITEMS.register("chameleon",
            () -> new ChameleonItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> REGENERATION_SYRING = ITEMS.register("regeneration_syringe",
            () -> new RegenerationSyringItem(new Item.Properties().stacksTo(1)));
}
