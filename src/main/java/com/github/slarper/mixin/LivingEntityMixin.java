package com.github.slarper.mixin;

import com.github.slarper.IcarusWingsMain;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getPreferredEquipmentSlot", at = @At(value = "HEAD"), cancellable = true)
    private static void getPreferredEquipmentSlotElytraItem(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir){

        Item item = stack.getItem();
        if (!stack.isOf(Items.CARVED_PUMPKIN) && (!(item instanceof BlockItem) || !(((BlockItem)item).getBlock() instanceof AbstractSkullBlock))) {
            if (item instanceof ArmorItem) {
                cir.setReturnValue(((ArmorItem)item).getSlotType());
            } else if (item instanceof ElytraItem) {
                cir.setReturnValue(EquipmentSlot.CHEST);
            } else {
                cir.setReturnValue(stack.isOf(Items.SHIELD) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND);
            }
        } else {
            cir.setReturnValue(EquipmentSlot.HEAD);
        }

    }

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);
    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);
    @Shadow
    protected int roll;

    @Inject(method = "tickFallFlying", at = @At(value = "HEAD"), cancellable = true)
    private void tickFallFlyingElytraItem(CallbackInfo ci){

        LivingEntity self = (LivingEntity) (Object) this;

        // flag 7 is whether fallflying.
/*        public boolean isFallFlying() {
            return this.getFlag(7);
        }*/
        boolean bl = this.getFlag(7);

        if (bl && !this.onGround && !this.hasVehicle() && !this.hasStatusEffect(StatusEffects.LEVITATION)) {

            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.CHEST);
            if (itemStack.getItem() instanceof ElytraItem && ElytraItem.isUsable(itemStack)) {
                int i = this.roll + 1;
                if (!this.world.isClient && i % 10 == 0) {
                    int j = i / 10;
                    if (j % 2 == 0) {
                        itemStack.damage(1,  self, (player) -> {
                            player.sendEquipmentBreakStatus(EquipmentSlot.CHEST);
                        });
                    }

                    this.emitGameEvent(GameEvent.ELYTRA_FREE_FALL);
                }
            }
        }

        if (!this.world.isClient) {
            this.setFlag(7, bl);
        }

        ci.cancel();

    }
}
