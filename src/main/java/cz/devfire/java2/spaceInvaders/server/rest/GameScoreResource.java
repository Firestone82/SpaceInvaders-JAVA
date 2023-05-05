package cz.devfire.java2.spaceInvaders.server.rest;

import cz.devfire.java2.spaceInvaders.server.database.model.GameScore;
import cz.devfire.java2.spaceInvaders.server.database.repository.GameScoreRepository;
import io.quarkus.panache.common.Sort;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.LinkedList;

@Log4j2(topic = "REST - GameScoreResource")
@Path("/")
public class GameScoreResource {

    @Inject
    GameScoreRepository gameScoreRepository;

    @GET
    @Path("scores")
    @Produces("application/json")
    public Collection<GameScore> getGameScores() {
        log.info("Get all gameScores");

        return gameScoreRepository.findAll().list();
    }

    @GET
    @Path("score/{id}")
    @Produces("application/json")
    public GameScore getGameScore(@PathParam("id") Long id) {
        log.info("Get gameScore: {}", id);

        return gameScoreRepository.findById(id);
    }

    @POST
    @Path("score")
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public GameScore createGameScore(GameScore gameScore) {
        log.info("Create gameScore: {}", gameScore);

        gameScoreRepository.persist(gameScore);
        return gameScore;
    }

    @POST
    @Path("scores")
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public Collection<GameScore> createGameScore(Collection<GameScore> gameScore) {
        Collection<GameScore> scores = new LinkedList<>();

        for (GameScore gs : gameScore) {
            log.info("Create gameScore: {}", gs);

            gameScoreRepository.persist(gs);
            scores.add(gs);
        }

        return scores;
    }

    @PUT
    @Path("score")
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public String updateGameScore(GameScore gameScore) {
        log.info("Update gameScore: {}", gameScore);

        GameScore gm = gameScoreRepository.findById(gameScore.getId());
        if (gm == null) {
            gameScoreRepository.persist(gameScore);
            return "GameScore inserted";
        }

        gm.setPlayerName(gameScore.getPlayerName());
        gm.setScore(gameScore.getScore());
        gm.setTime(gameScore.getTime());

        gameScoreRepository.getEntityManager().merge(gm);
        return "GameScore updated";
    }

    @DELETE
    @Transactional
    @Path("scores")
    @Produces("application/json")
    public String clearScores() {
        log.info("Clearing scores");

        for (GameScore gs : gameScoreRepository.findAll().list()) {
            log.info(" |- Deleting gameScore: {}", gs);
            gameScoreRepository.delete(gs);
        }

        return "Scores cleared";
    }

    @DELETE
    @Transactional
    @Path("score/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean deleteCourse(@PathParam("id") Long id) {
        log.info("Delete gameScore: {}", id);

        return gameScoreRepository.deleteById(id);
    }

    @GET
    @Path("leaderboard")
    @Produces("application/json")
    public Collection<GameScore> getLeaderboard() {
        log.info("Get leaderboard");

        return gameScoreRepository.findAll(Sort.descending("score")).range(0,6).list();
    }

}
