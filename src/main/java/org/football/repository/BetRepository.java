package org.football.repository;

import org.football.model.Bet;
import org.football.model.Match;
import org.football.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findBetsByMatch(Match match);

    List<Bet> findBetsByMatchAndTeam(Match match, Team team);
}