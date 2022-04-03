package com.github.slarper.mixin.common;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.slarper.item.RegisterItems.ICARUS_WINGS;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "getPreferredEquipmentSlot",at = @At(value = "HEAD"), cancellable = true)
    private static void getPreferredEquipmentSlot0(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir){
        if (stack.isOf(ICARUS_WINGS)){
            cir.setReturnValue(EquipmentSlot.CHEST);
        }
    }
}
