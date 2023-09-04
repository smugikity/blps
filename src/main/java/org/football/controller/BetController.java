package org.football.controller;

import org.football.exception.ResourceNotFoundException;
import org.football.model.Bet;
import org.football.model.Team;
import org.football.model.User;
import org.football.dto.BetDto;
import org.football.repository.MatchRepository;
import org.football.repository.TeamRepository;
import org.football.repository.BetRepository;
import org.football.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class BetController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    // get all teams
    @GetMapping("/bets")
    public List<Bet> getAllBets(){
        return betRepository.findAll();
    }

    // create team rest api
    @PostMapping("/bets")
    public ResponseEntity<String> createBet(@RequestBody BetDto betDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user not exist with username"));
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setPoint(betDto.getPoint());
        bet.setMatch(matchRepository.findById(betDto.getMatch()).orElseThrow(() -> new ResourceNotFoundException("match not exist with id")));
        bet.setTeam(teamRepository.findById(betDto.getTeam()).orElseThrow(() -> new ResourceNotFoundException("team not exist with id")));
        if (user.getPoint()>=bet.getPoint()) {
            user.setPoint(user.getPoint() - bet.getPoint());
            betRepository.save(bet);
            return new ResponseEntity<>("Placed bet", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not enough points", HttpStatus.BAD_REQUEST);
    }

    // get bet by id rest api
    @GetMapping("/bets/{id}")
    public ResponseEntity<Bet> getBetById(@PathVariable Long id) {
        Bet bet = betRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("team not exist with id :" + id));
        return ResponseEntity.ok(bet);
    }

    // update team rest api

//    @PutMapping("/teams/{id}")
//    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team teamDetails){
//        Team team = teamRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("team not exist with id :" + id));
//
//        team.setName(teamDetails.getName());
//        team.setCity(teamDetails.getCity());
//        team.setStadium(teamDetails.getStadium());
//
//        Team updatedTeam = teamRepository.save(team);
//        return ResponseEntity.ok(updatedTeam);
//    }
//
//    // delete team rest api
//    @DeleteMapping("/teams/{id}")
//    public ResponseEntity<Map<String, Boolean>> deleteTeam(@PathVariable Long id){
//        Team team = teamRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("team not exist with id :" + id));
//
//        teamRepository.delete(team);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return ResponseEntity.ok(response);
//    }
}