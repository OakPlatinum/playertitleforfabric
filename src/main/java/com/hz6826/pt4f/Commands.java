package com.hz6826.pt4f;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.EntitySelector;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.command.argument.EntityArgumentType.*;
import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class Commands {
    public static final String ROOT_COMMAND_NAME = "playertitle";
    public static final String FEEDBACK_PREFIX = "§b[PT4F]§r ";

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal(ROOT_COMMAND_NAME).requires(source -> source.hasPermissionLevel(2))
                .then(literal("reload").executes(Commands::reloadCommand))
                .then(literal("grant")
                        .then(argument("player", player()).then(argument("title", greedyString()).executes(Commands::grantTitleCommand)))
                        .then(argument("players", players()).then(argument("title", greedyString()).executes(Commands::grantTitleCommandMultiple)))
                        .then(literal("-o").then(argument("offline_player_name", word()).then(argument("title", greedyString()).executes(Commands::grantTitleCommandOffline))))
                )
                .then(literal("revoke")
                        .then(argument("player", player()).executes(Commands::revokeTitleCommand))
                        .then(argument("players", players()).executes(Commands::revokeTitleCommandMultiple))
                        .then(literal("-o").then(argument("offline_player_name", word()).executes(Commands::revokeTitleCommandOffline)))
                        .then(literal("all").executes(Commands::revokeTitleCommandAll))
                )
        ));
        // CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal(Config.rootCommandAlias).redirect(dispatcher.getRoot().getChild(ROOT_COMMAND_NAME))));
    }

    public static int reloadCommand(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().sendFeedback(() -> Text.literal(FEEDBACK_PREFIX + "Reloading!"), false);
        MidnightConfig.init(PlayerTitleForFabric.MOD_ID, Config.class);
        context.getSource().sendFeedback(() -> Text.literal(FEEDBACK_PREFIX + "Done!"), false);
        return 1;
    }

    public static int grantTitleCommand(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerTitleManager.grantPlayerTitle(context.getArgument("player", EntitySelector.class).getPlayer(context.getSource()).getUuid().toString(), context.getArgument("title", String.class), true);
        context.getSource().sendFeedback(() -> Text.literal(FEEDBACK_PREFIX + "Done!"), false);
        return 1;
    }

    public static int grantTitleCommandMultiple(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        for (PlayerEntity player : context.getArgument("players", EntitySelector.class).getPlayers(context.getSource())) {
            PlayerTitleManager.grantPlayerTitle(player.getUuid().toString(), context.getArgument("title", String.class), false);
        }
        postRun(context);
        return 1;
    }

    public static int grantTitleCommandOffline(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerTitleManager.grantPlayerTitle(context.getArgument("offline_player_name", String.class), context.getArgument("title", String.class), true);
        postRun(context);
        return 1;
    }

    public static int revokeTitleCommand(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerTitleManager.revokePlayerTitle(context.getArgument("player", EntitySelector.class).getPlayer(context.getSource()).getUuid().toString());
        postRun(context);
        return 1;
    }

    public static int revokeTitleCommandMultiple(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        for (PlayerEntity player : context.getArgument("players", EntitySelector.class).getPlayers(context.getSource())) {
            PlayerTitleManager.revokePlayerTitle(player.getUuid().toString());
        }
        postRun(context);
        return 1;
    }

    public static int revokeTitleCommandOffline(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerTitleManager.revokePlayerTitle(context.getArgument("offline_player_name", String.class));
        postRun(context);
        return 1;
    }

    public static int revokeTitleCommandAll(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerTitleManager.revokeAllPlayerTitles();
        postRun(context);
        return 1;
    }

    public static void postRun(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().sendFeedback(() -> Text.literal(FEEDBACK_PREFIX + "Done!"), false);
    }
}
