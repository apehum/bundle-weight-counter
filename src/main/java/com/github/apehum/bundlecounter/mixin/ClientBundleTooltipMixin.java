package com.github.apehum.bundlecounter.mixin;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if <26.1 {
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
//?} else {
/*import org.apache.commons.lang3.math.Fraction;
*///?}

@Mixin(ClientBundleTooltip.class)
public abstract class ClientBundleTooltipMixin implements ClientTooltipComponent {

    //? if >=26.1 {
    /*@Inject(method = "getProgressBarFillText", at = @At("RETURN"), cancellable = true)
    private static void getProgressBarFillText(Fraction weight, CallbackInfoReturnable<Component> cir) {
        var text = cir.getReturnValue();
        if (text != null) return;

        var stackValue = (int) (weight.floatValue() * 64);
        var newText = Component.literal(stackValue + "/64");

        cir.setReturnValue(newText);
    }
    *///?} else {
    @Shadow @Final private BundleContents contents;

    @Inject(method = "getProgressBarFillText", at = @At("RETURN"), cancellable = true)
    private void getProgressBarFillText(CallbackInfoReturnable<Component> cir) {
        var text = cir.getReturnValue();
        if (text != null) return;

        var weight = contents.weight();
        var stackValue = (int) (weight.floatValue() * 64);
        var newText = Component.literal(stackValue + "/64");

        cir.setReturnValue(newText);
    }
    //?}
}
