package com.prot1n.totemancy.init;

import com.prot1n.totemancy.Totemancy;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class InitBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Totemancy.MODID);

    public static final RegistryObject<Block> OAK_WITHER_TOTEM = register(
            "oak_wither_totem",
            () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).requiresCorrectToolForDrops().sound(SoundType.WOOD)),
            new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)
    );

    public static final RegistryObject<Block> OAK_POISON_TOTEM = register(
            "oak_poison_totem",
            () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).requiresCorrectToolForDrops().sound(SoundType.WOOD)),
            new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)
    );

    public static final RegistryObject<Block> OAK_FIRE_RESISTANCE_TOTEM = register(
            "oak_fire_resistance_totem",
            () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).requiresCorrectToolForDrops().sound(SoundType.WOOD)),
            new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)
    );

    public static <T extends Block> RegistryObject<T> register(
            String name,
            Supplier<T> supplier,
            Item.Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        InitItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }
}
