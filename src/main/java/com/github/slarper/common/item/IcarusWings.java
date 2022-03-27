package com.github.slarper.common.item;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class IcarusWings extends Item implements FabricElytraItem {

    public IcarusWings(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(itemStack);
        ItemStack itemStack2 = user.getEquippedStack(equipmentSlot);
        if (itemStack2.isEmpty()) {
            user.equipStack(equipmentSlot, itemStack.copy());
            if (!world.isClient()) {
                user.incrementStat(Stats.USED.getOrCreateStat(this));
            }

            itemStack.setCount(0);
            return TypedActionResult.success(itemStack, world.isClient());
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    @Override
    public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
        if (chestStack.getDamage() < chestStack.getMaxDamage()) {
            if (tickElytra) {
                doVanillaElytraTick(entity, chestStack);
            }

            return true;
        }

        return false;
    }

    @Override
    public void doVanillaElytraTick(LivingEntity entity, ItemStack chestStack) {
        int nextRoll = entity.getRoll() + 1;

        if (!entity.world.isClient && nextRoll % 10 == 0) {
            if ((nextRoll / 10) % 2 == 0) {
                int damageAmount;

                World world = entity.getWorld();
                long timeOfDay = world.getTimeOfDay();
                int sunLevel = getSunLevel(timeOfDay);
                int skyLight = world.getLightLevel(LightType.SKY, entity.getBlockPos());

                damageAmount = 1 + getMeltLevel(sunLevel, skyLight);

                chestStack.damage(damageAmount, entity, p -> p.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
            }

            entity.emitGameEvent(GameEvent.ELYTRA_FREE_FALL);
        }
    }

    public int getSunLevel(long timeOfDay){
        if (timeOfDay >= 1000 && timeOfDay < 6000) {
            return (int) (timeOfDay * 15 / 5000);
        } else if ( timeOfDay>=6000 && timeOfDay < 11000){
            return (int) ((10000 - timeOfDay) * 15 / 5000);
        } else {
            return 0;
        }
    }

    public int getMeltLevel(int sunLevel, int skyLight){
        return sunLevel * skyLight / 15 / 3;
    }
}
