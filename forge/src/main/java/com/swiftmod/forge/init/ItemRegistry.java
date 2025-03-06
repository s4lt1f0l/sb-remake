package com.swiftmod.forge.init;

import com.swiftmod.forge.AspectOfTheEnd;
import com.swiftmod.forge.ExampleModForge;
import com.swiftmod.forge.Hyperion;
import com.swiftmod.forge.Terminator;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.swiftmod.ExampleMod;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MOD_ID);
    public static final RegistryObject<Item> aote = ITEMS.register("aspect_of_the_end",
            () -> new AspectOfTheEnd(Tiers.DIAMOND, 8,3, new Item.Properties()
                    .durability(2000)));
    public static final RegistryObject<Item> hyperion = ITEMS.register("hyperion",
            () -> new Hyperion(Tiers.IRON,10,2,new Item.Properties()
            .durability(4000)
            .fireResistant()));
    public static final RegistryObject<Item> terminator = ITEMS.register("terminator",()-> new Terminator(new Item.Properties()
            .durability(2500)
            .fireResistant()));
}
