package com.hz6826.pt4f;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.List;

public class Config extends MidnightConfig {
    public static final String BASIC = "Basic";
    public static final String DEFAULT_VALUES = "Default Values";
    public static final String PLAYER_TITLE_DATA = "Player Title Data";


    @Entry(category = BASIC, name = "running mode")
    public static String runningMode = "api";  // "api" or "mixin" TODO
    @Entry(category = BASIC, name = "root command alias")
    public static String rootCommandAlias = PlayerTitleForFabric.MOD_ID;

    @Entry(category = DEFAULT_VALUES, name = "default title")
    public static String defaultTitle = "";
    @Entry(category = DEFAULT_VALUES, name = "prefix")
    public static String prefix = "";
    @Entry(category = DEFAULT_VALUES, name = "suffix")
    public static String suffix = " ";

    @Entry(category = PLAYER_TITLE_DATA, name = "player title data")
    public static List<String> playerTitleData = List.of();
}
