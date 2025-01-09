package com.hz6826.pt4f;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerTitleForFabric implements ModInitializer {
	public static final String MOD_ID = "pt4f";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Player Title For Fabric is loading!");
		MidnightConfig.init(MOD_ID, Config.class);
		Commands.init();
		ServerMessageDecoratorEvent.EVENT.register(ServerMessageDecoratorEvent.CONTENT_PHASE, DecoratorEventHandler::decorate);
	}
}