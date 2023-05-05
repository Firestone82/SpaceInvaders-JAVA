package cz.devfire.java2.spaceInvaders.client.file;

import cz.devfire.java2.spaceInvaders.client.Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class Language {
    private static final Map<String, Language> localeMap = new HashMap<>();
    private static String DEFAULT_LOCALE = "en_US";
    private static Properties language;

    public static final Language STATUS_ENTER_NAME = new Language("status.enterName");
    public static final Language STATUS_GAME_OVER = new Language("status.gameOver");
    public static final Language STATUS_GAME_OVER_DESC = new Language("status.gameOverDesc");
    public static final Language STATUS_GAME_PAUSED = new Language("status.gamePaused");
    public static final Language STATUS_GAME_PAUSED_DESC = new Language("status.gamePausedDesc");
    public static final Language BUTTON_LEADERBOARD = new Language("button.leaderboard");
    public static final Language BUTTON_NEW_GAME = new Language("button.newGame");
    public static final Language BUTTON_BACK = new Language("button.back");
    public static final Language GAME_USERNAME = new Language("game.username");
    public static final Language GAME_SCORE = new Language("game.score");
    public static final Language GAME_LIVES = new Language("game.lives");
    public static final Language GAME_TIME = new Language("game.time");
    public static final Language ERROR_TITLE = new Language("errors.title");
    public static final Language ERROR_NO_NAME= new Language("errors.noName");
    public static final Language ERROR_NO_DATA= new Language("errors.noData");

    private String message;

    private Language(String identifier) {
        localeMap.put(identifier, this);
    }

    public static void reload(Client client) {
        DEFAULT_LOCALE = client.getConfig().getProperty("lang");

        try {
            language = new Properties();
            language.load(Language.class.getResourceAsStream("/lang/" + DEFAULT_LOCALE + ".properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String identifier : localeMap.keySet()) {
            localeMap.get(identifier).message = language.getProperty(identifier).replace("\"", "");
        }
    }

    public String get(Object... objects) {
        if (message != null && !message.isEmpty()) {
            String msg = message;

            for (int i = 0; i < objects.length; i++)
                msg = msg.replace("{" + i + "}", objects[i].toString());

            return msg;
        }

        if (message.isEmpty()) {
            return "";
        }

        return "SpaceInvaders-Clone Message";
    }
}
