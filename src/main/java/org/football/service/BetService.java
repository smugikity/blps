package org.football.service;

import org.football.model.Bet;
import org.football.model.Match;
import org.football.model.Team;

import java.util.List;

public interface BetService {
    List<Bet> findAll();

    Bet findById(Long id) throws Exception;

    List<Bet> findBetsByMatch(Match match);

    List<Bet> findBetsByMatchAndTeam(Match match, Team team);

    Bet createBet(String username, Integer point, Long matchId, Long teamId) throws Exception;
}
