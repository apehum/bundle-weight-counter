package com.github.apehum.bundlecounter.mixin;

import com.github.apehum.bundlecounter.BundleCounter;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
    @WrapOperation(
            method = "renderItemCount",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getCount()I")
    )
    private int getCount(ItemStack instance, Operation<Integer> original) {
        if (!BundleCounter.CONFIG.shouldRenderOnItem) {
            return original.call(instance);
        }

        if (instance.getCount() != 1) {
            return original.call(instance);
        }

        BundleContents contents = instance.get(DataComponents.BUNDLE_CONTENTS);
        if (contents == null) {
            return original.call(instance);
        }

        var weight = contents.weight();
        var stackValue = (int) (weight.floatValue() * 64);

        return Math.max(1, stackValue);
    }
}
