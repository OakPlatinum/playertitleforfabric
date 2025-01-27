package com.hz6826.pt4f;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerTitleManager {
    private static final Map<String, Text> playerTitleMap = new HashMap<>();
    private static Text defaultTitle = Text.empty();
    private static int hashCode = 0; // Config.playerTitleData.hashCode();

    public static void update() {
        List<String> playerTitleData = Config.playerTitleData;
        if (hashCode != playerTitleData.hashCode()) {
            hashCode = playerTitleData.hashCode();
            playerTitleMap.clear();
            for (String entry : playerTitleData) {
                String[] parts = entry.split(",");
                if (parts.length == 2) {
                    String uuidStringOrPlayerName = parts[0];
                    String titleText = parts[1];
                    String modifiedTitleText = titleText
                            .replace("&&", "{{TEMP}}")
                            .replace("&", "§")
                            .replace("{{TEMP}}", "&");
                    MutableText text = Text.literal(Config.prefix + modifiedTitleText + Config.suffix);
                    playerTitleMap.put(uuidStringOrPlayerName, text);
                } else {
                    PlayerTitleForFabric.LOGGER.error("Invalid entry in player title data: " + entry);
                }
            }
        }
        defaultTitle = Config.defaultTitle.isEmpty() ? Text.empty() : Text.literal(Config.prefix + Config.defaultTitle + Config.suffix);
    }

    public static Text getPlayerTitle(UUID uuid, String playerName) {  // UUID first, then player name
        update();
        return playerTitleMap.getOrDefault(uuid.toString(), playerTitleMap.getOrDefault(playerName, defaultTitle));
    }

    public static void grantPlayerTitle(String UUIDOrPlayerName, String title, boolean overwrite) {
        Config.playerTitleData.stream().filter(entry -> entry.startsWith(UUIDOrPlayerName + ",")).findFirst().ifPresentOrElse(entry -> {
            if (overwrite) {
                Config.playerTitleData.removeIf(entry2 -> entry2.startsWith(UUIDOrPlayerName + ","));
                Config.playerTitleData.add(UUIDOrPlayerName + "," + title);
            }
        }, () -> Config.playerTitleData.add(UUIDOrPlayerName + "," + title));
        Config.write(PlayerTitleForFabric.MOD_ID);
        update();
    }

    public static void revokePlayerTitle(String UUIDOrPlayerName) {
        Config.playerTitleData.removeIf(entry -> entry.startsWith(UUIDOrPlayerName + ","));
        Config.write(PlayerTitleForFabric.MOD_ID);
        update();
    }

    public static void revokeAllPlayerTitles() {
        Config.playerTitleData.clear();
        Config.write(PlayerTitleForFabric.MOD_ID);
        update();
    }
}
