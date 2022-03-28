package com.github.slarper.common.item;

import com.github.slarper.IcarusWingsMain;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegistryItems {

    public static void load(){}

    public static final Item ICARUS_WINGS;

    private static Item register(String id, Item item){
        return Registry.register(Registry.ITEM, new Identifier(IcarusWingsMain.MODID, id), item);
    }

    static {

        ICARUS_WINGS = register( "icarus_wings", new IcarusWings((new Item.Settings()).maxDamage(216).group(ItemGroup.TRANSPORTATION)));

    }
}
