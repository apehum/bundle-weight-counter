package com.github.apehum.bundlecounter.config;

import com.github.apehum.bundlecounter.BundleCounter;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
		}
		else {
			JsonObject root = new JsonObject();
			try {
				root = JsonParser.parseString(Files.readString(Config.FILE_PATH)).getAsJsonObject();
			}
			catch (IOException e) {
				BundleCounter.LOGGER.error("Failed to read config file!", e);
			}

			if (root.has("shouldRenderOnItem")) {
				config.shouldRenderOnItem = root.get("shouldRenderOnItem").getAsBoolean();
			}
		}

		return config;
	}

	public void writeToFile() {
		JsonObject root = new JsonObject();
		root.addProperty("shouldRenderOnItem", this.shouldRenderOnItem);

		try {
			Files.writeString(FILE_PATH, new GsonBuilder().setPrettyPrinting().create().toJson(root));
		}
		catch (IOException e)
		{
			BundleCounter.LOGGER.error("Failed to write config file!", e);
		}
	}
}
