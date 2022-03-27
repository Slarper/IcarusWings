package com.github.slarper.mixin;

import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract Item getItem();

    @Inject(method = "isOf", at = @At(value = "HEAD"),cancellable = true)
    private void isOfELYTRA(Item item, CallbackInfoReturnable<Boolean> cir){
        if (item == Items.ELYTRA){
            cir.setReturnValue(this.getItem() instanceof ElytraItem);
        }
    }
}
