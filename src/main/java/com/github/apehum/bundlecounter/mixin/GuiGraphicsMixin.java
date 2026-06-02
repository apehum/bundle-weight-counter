package com.github.apehum.bundlecounter.mixin;

import com.github.apehum.bundlecounter.BundleCounter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
    @ModifyVariable(
            method = "renderItemCount",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private String modifyCountString(String original, Font font, ItemStack itemStack, int i, int j) {
        if (original != null) {
            return original;
        }

        if (!BundleCounter.CONFIG.shouldRenderOnItem) {
            return null;
        }

        if (itemStack.getCount() != 1) {
            return null;
        }

        BundleContents contents = itemStack.get(DataComponents.BUNDLE_CONTENTS);
        if (contents == null) {
            return null;
        }

        //? if >=26.1 {
        /*var weightResult = contents.weight();
        float weight = weightResult.isError() ? 1 : weightResult.getOrThrow().floatValue();
        *///?} else {
        float weight = contents.weight().floatValue();
        //?}
        var stackValue = (int) (weight * 64);

        var count = Math.max(0, stackValue);
        if (count == 0) {
            return null;
        }

        if (count == 1 && !BundleCounter.CONFIG.shouldRenderSingleCount) {
            return null;
        }

        return String.valueOf(count);
    }
}
