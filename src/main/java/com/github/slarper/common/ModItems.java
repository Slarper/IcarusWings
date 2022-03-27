package com.github.slarper.common;

import com.github.slarper.IcarusWingsMain;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item ICARUS_WINGS;
    public static final Item TURTLE_LEGGINgS;

    static {
        ICARUS_WINGS = Registry.register(Registry.ITEM, new Identifier(IcarusWingsMain.MODID, "icarus_wings"), (Item)(new ElytraItem((new Item.Settings()).maxDamage(432).group(ItemGroup.TRANSPORTATION).rarity(Rarity.UNCOMMON))));

        TURTLE_LEGGINgS = Registry.register(Registry.ITEM, new Identifier(IcarusWingsMain.MODID, "turtle_leggings"), (Item)(new ArmorItem(ArmorMaterials.TURTLE, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT))));

    }

}