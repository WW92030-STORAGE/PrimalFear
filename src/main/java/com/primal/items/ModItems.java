package com.primal.items;

import com.primal.util.Reference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);

    public static final RegistryObject<Item> PRIMAL = ITEMS.register("primal",
            () -> new PrimalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 8));
    public static final RegistryObject<Item> PRIMAL2 = ITEMS.register("primal2",
            () -> new PrimalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 12));
    public static final RegistryObject<Item> PRIMAL3 = ITEMS.register("primal3",
            () -> new PrimalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 16));
    public static final RegistryObject<Item> PRIMAL4 = ITEMS.register("primal4",
            () -> new PrimalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 20));
    public static final RegistryObject<Item> PRIMAL5 = ITEMS.register("primal5",
            () -> new PrimalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 24));

    public static void register(IEventBus ieb) {
        ITEMS.register(ieb);
    }
}
