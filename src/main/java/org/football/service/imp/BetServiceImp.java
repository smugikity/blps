package org.football.service.imp;

import org.football.exception.ResourceNotFoundException;
import org.football.model.Bet;
import org.football.model.Match;
import org.football.model.Team;
import org.football.model.User;
import org.football.repository.BetRepository;
import org.football.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BetServiceImp implements BetService {
    @Autowired
    BetRepository betRepository;
    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    MatchServiceImp matchServiceImp;
    @Autowired
    TeamServiceImp teamServiceImp;

    @Override
    public List<Bet> findAll() {
        return betRepository.findAll();
    }

    @Override
    public Bet findById(Long id) throws Exception {
        return betRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bet not found"));

    }

    @Override
    public List<Bet> findBetsByMatch(Match match) {
        return betRepository.findBetsByMatch(match);
    }

    @Override
    public List<Bet> findBetsByMatchAndTeam(Match match, Team team) {
        return betRepository.findBetsByMatchAndTeam(match, team);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Bet createBet(String username, Integer point, Long matchId, Long teamId) throws Exception {
        User user = userServiceImp.findByUsername(username);
        Match match = matchServiceImp.findById(matchId);
        Team team = teamServiceImp.findById(teamId);
        if (point > user.getPoint()) throw new Exception("Not enough points");
        if (!(team.equals(match.getTeam1()) || team.equals(match.getTeam2())))
            throw new Exception("Chosen team is invalid");
        user.setPoint(user.getPoint() - point);
        user = userServiceImp.save(user);
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setPoint(point);
        bet.setTeam(team);
        bet.setMatch(match);
        bet = betRepository.save(bet);
        return bet;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Bet save(Bet bet) {
        return betRepository.save(bet);
    }
}
