package cz.devfire.java2.spaceInvaders.client.object.controller.list;

import cz.devfire.java2.spaceInvaders.client.Client;
import cz.devfire.java2.spaceInvaders.client.object.controller.MainController;
import cz.devfire.java2.spaceInvaders.server.database.model.GameScore;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@Log4j2(topic = "Game - ScoreController")
public class ScoreController {
    private final MainController mainController;

    private final HashMap<String, GameScore> list = new HashMap<>();

    public ScoreController(Client client, MainController mainController) {
        this.mainController = mainController;
    }

    public void loadScore() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("Loading scores...");
            int count = 0;

            try {
                URL url = new URL("http://localhost:8080/leaderboard");
                StringBuilder output = new StringBuilder();

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.connect();

                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext()) {
                    output.append(sc.nextLine());
                }

                JSONParser parse = new JSONParser();
                JSONArray object = (JSONArray) parse.parse(output.toString());

                for (Object o : object) {
                    JSONObject scoreObject = (JSONObject) o;

                    list.put((String) scoreObject.get("playerName"),
                            GameScore.builder()
                                    .id(((Long) scoreObject.get("id")).intValue())
                                    .playerName((String) scoreObject.get("playerName"))
                                    .score(((Long) scoreObject.get("score")).intValue())
                                    .time(((Long) scoreObject.get("time")).longValue())
                                    .build());
                    count++;
                }

                conn.disconnect();
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }

            return count;
        }).thenApply((count) -> {
            log.info("Loaded " + count + " scores");
            return count;
        }).exceptionally((e) -> {
            log.error("Failed to load scores");
            e.printStackTrace();
            return 0;
        });

        future.join();
    }

    public void saveScores() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("Saving scores");
            int total = 0;

            for (GameScore score : List.copyOf(list.values())) {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/score").openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.connect();

                    JSONObject scoreObject = new JSONObject();
                    scoreObject.put("id", score.getId());
                    scoreObject.put("playerName", score.getPlayerName());
                    scoreObject.put("score", score.getScore());
                    scoreObject.put("time", score.getTime());

                    conn.getOutputStream().write(scoreObject.toJSONString().getBytes());
                    conn.getOutputStream().flush();

                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                    } else {
                        total++;
                    }

                    conn.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return total;
        }).thenApply((total) -> {
            log.info("Scores saved: {} ", total);
            return total;
        }).exceptionally((e) -> {
            log.error("Failed to save scores");
            e.printStackTrace();
            return null;
        });

        future.join();
    }

    public void saveScore(String player, GameScore score) {
        if (list.containsKey(player)) {
            GameScore oldScore = list.get(player);

            if (oldScore.getScore() < score.getScore()) {
                list.put(player, score);
            }
        } else {
            list.put(player, score);
        }
    }

    public void clearScore() {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("Clearing scores...");
            boolean success = false;

            try {
                URL url = new URL("http://localhost:8080/scores");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Accept", "application/json");
                conn.connect();

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                conn.disconnect();
            } catch (IOException er) {
                log.error("Failed to clear scores");
                throw new RuntimeException(er);
            }

            return success;
        }).thenRun(() -> {
            log.info("Scores cleared");
        }).thenRun(list::clear);

        future.join();
    }

    public Collection<GameScore> getScoreList() {
        return list.values();
    }
}
