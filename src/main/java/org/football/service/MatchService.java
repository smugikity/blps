package org.football.service;

import org.football.exception.ResourceNotFoundException;
import org.football.model.Match;
import org.football.model.Team;
import org.football.repository.BetRepository;
import org.football.repository.MatchRepository;
import org.football.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {
    @Autowired
    MatchRepository matchRepository;

    @Autowired
    BetRepository betRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Match updateScore(long id, byte team1_score, byte team2_score) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("match not exist with id :" + id));

        match.setTeam1_score(team1_score);
        match.setTeam2_score(team2_score);

        if (match.getTeam1_score()==match.getTeam2_score()) {
            betRepository.findBetsByMatch(match).forEach(bet->{
                bet.getUser().setPoint(bet.getUser().getPoint()+bet.getPoint());
                userRepository.save(bet.getUser());
            });
        } else {
            Team winningTeam = match.getTeam1_score()>match.getTeam2_score()?match.getTeam1():match.getTeam2();
            betRepository.findBetsByMatchAndTeam(match,winningTeam).forEach(bet->{
                bet.getUser().setPoint(bet.getUser().getPoint()+bet.getPoint()*2);
                userRepository.save(bet.getUser());
            });
        }
        return matchRepository.save(match);
    }
}
