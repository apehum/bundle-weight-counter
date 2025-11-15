//? if neoforge {
/*package com.github.apehum.bundlecounter;

import com.github.apehum.bundlecounter.config.ConfigScreen;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod("bundleweightcounter")
public class NeoForgeBundleCounter {
    public NeoForgeBundleCounter(ModContainer container) {
        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (modContainer, parent) -> ConfigScreen.createConfigScreen(parent)
        );
    }
}
*///?}
