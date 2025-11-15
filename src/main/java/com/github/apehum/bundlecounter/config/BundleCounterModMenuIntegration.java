//? if fabric {
package com.github.apehum.bundlecounter.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class BundleCounterModMenuIntegration implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory()
	{
		return ConfigScreen::createConfigScreen;
	}
}
//?}
