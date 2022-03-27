package com.github.slarper.mixin;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "getPreferredEquipmentSlot",at = @At(value = "HEAD"),cancellable = true)
    private static void getPreferredEquipmentSlot0(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir){
        if (stack.getItem() instanceof FabricElytraItem){
            cir.setReturnValue(EquipmentSlot.CHEST);
        }
    }
}
