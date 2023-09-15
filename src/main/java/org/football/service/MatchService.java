package org.football.service;

import org.football.model.Match;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MatchService {
    Match updateScore(long id, byte team1_score, byte team2_score) throws Exception;

    Match findById(Long id) throws Exception;

    List<Match> findAll();

    Match create(String name,Long team1, Long team2) throws Exception;

    void delete(Long id) throws Exception;
}
