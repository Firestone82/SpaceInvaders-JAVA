package cz.devfire.java2.spaceInvaders.server.database.repository;

import cz.devfire.java2.spaceInvaders.server.database.model.GameScore;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameScoreRepository implements PanacheRepository<GameScore> {

}