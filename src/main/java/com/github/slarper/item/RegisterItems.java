package com.github.slarper.item;

import com.github.slarper.IcarusWings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterItems {

    public static void load(){}

    public static final Item ICARUS_WINGS;

    private static Item register(String id, Item item){
        return Registry.register(Registry.ITEM, new Identifier(IcarusWings.MODID, id), item);
    }

    static {

        ICARUS_WINGS = register( "icarus_wings", new IcarusWingsItem((new Item.Settings()).maxDamage(216).group(ItemGroup.TRANSPORTATION)));

    }
}
