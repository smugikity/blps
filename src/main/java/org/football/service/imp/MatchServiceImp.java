package org.football.service.imp;

import org.football.exception.ResourceNotFoundException;
import org.football.model.Match;
import org.football.model.Team;
import org.football.repository.BetRepository;
import org.football.repository.MatchRepository;
import org.football.repository.TeamRepository;
import org.football.repository.UserRepository;
import org.football.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchServiceImp implements MatchService {
    @Autowired
    MatchRepository matchRepository;

    @Autowired
    BetRepository betRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Match updateScore(long id, byte team1_score, byte team2_score) throws Exception {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("match not exist with id :" + id));

        match.setTeam1_score(team1_score);
        match.setTeam2_score(team2_score);

        if (match.getTeam1_score() == match.getTeam2_score()) {
            betRepository.findBetsByMatch(match).forEach(bet -> {
                bet.getUser().setPoint(bet.getUser().getPoint() + bet.getPoint());
                userRepository.save(bet.getUser());
            });
        } else {
            Team winningTeam = match.getTeam1_score() > match.getTeam2_score() ? match.getTeam1() : match.getTeam2();
            betRepository.findBetsByMatchAndTeam(match, winningTeam).forEach(bet -> {
                bet.getUser().setPoint(bet.getUser().getPoint() + bet.getPoint() * 2);
                userRepository.save(bet.getUser());
            });
        }
        return matchRepository.save(match);
    }

    @Override
    public Match findById(Long id) throws Exception {
        return matchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Match not found"));
    }

    @Override
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Match create(String name, Long team1, Long team2) throws Exception {
        Match match = new Match();
        match.setName(name);
        match.setTeam1(teamRepository.findById(team1).orElseThrow(() -> new ResourceNotFoundException("team not exist with id")));
        match.setTeam2(teamRepository.findById(team2).orElseThrow(() -> new ResourceNotFoundException("team not exist with id")));
        match = matchRepository.save(match);
        return match;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) throws Exception {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("match not exist with id :" + id));

        matchRepository.delete(match);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Match save(Match match) {
        return matchRepository.save(match);
    }
}
