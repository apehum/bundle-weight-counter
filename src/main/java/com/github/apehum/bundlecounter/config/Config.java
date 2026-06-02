package com.github.apehum.bundlecounter.config;

import com.github.apehum.bundlecounter.BundleCounter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//? if fabric {
import net.fabricmc.loader.api.FabricLoader;
//?} else {
/*import net.neoforged.fml.loading.FMLPaths;
*///?}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
	public boolean shouldRenderOnItem = true;
	public boolean shouldRenderSingleCount = false;

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final String FILE_NAME = "bundle_counter.json";

	//? if fabric {
	private static final Path FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
	//?} else {
	/*private static final Path FILE_PATH = FMLPaths.CONFIGDIR.get().resolve(FILE_NAME);
	*///?}

	public static Config loadFromFile() {
		Config config = new Config();
		if (Files.notExists(FILE_PATH)) {
			config.writeToFile();
		} else {
			try {
				config = GSON.fromJson(Files.readString(Config.FILE_PATH), Config.class);
			} catch (IOException e) {
				BundleCounter.LOGGER.error("Failed to read config file!", e);
			}
		}

		return config;
	}

	public void writeToFile() {
		try {
			Files.writeString(FILE_PATH, GSON.toJson(this));
		} catch (IOException e) {
			BundleCounter.LOGGER.error("Failed to write config file!", e);
		}
	}
}
