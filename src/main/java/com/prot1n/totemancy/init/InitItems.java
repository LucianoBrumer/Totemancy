package com.prot1n.totemancy.init;

import com.prot1n.totemancy.Totemancy;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, Totemancy.MODID
    );

    public static final RegistryObject<Item> POKEBOLA = ITEMS.register(
            "pokeball", () -> new Item(new Item.Properties()
                    .tab(CreativeModeTab.TAB_MISC)
            )
    );
}
