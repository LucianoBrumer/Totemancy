package com.prot1n.totemancy;

import com.prot1n.totemancy.events.TotemEvents;
import com.prot1n.totemancy.init.InitBlocks;
import com.prot1n.totemancy.init.InitItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

@Mod(Totemancy.MODID)
public class Totemancy {
    public static final String MODID = "totemancy";

    public Totemancy(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        InitItems.ITEMS.register(bus);
        InitBlocks.BLOCKS.register(bus);
        EVENT_BUS.register(new TotemEvents());
    }
}
