package org.football.controller;

import org.football.exception.ResourceNotFoundException;
import org.football.jaas.CustomJaasPrincipal;
import org.football.model.Bet;
import org.football.model.Team;
import org.football.model.User;
import org.football.dto.BetDto;
import org.football.repository.MatchRepository;
import org.football.repository.TeamRepository;
import org.football.repository.BetRepository;
import org.football.repository.UserRepository;
import org.football.service.BetService;
import org.football.service.imp.BetServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class BetController {
    @Autowired
    private BetServiceImp betServiceImp;

    // get all teams
    @GetMapping("/bets")
    public List<Bet> getAllBets(){
        return betServiceImp.findAll();
    }

    // create team rest api
    @PostMapping("/bets")
    public ResponseEntity<?> createBet(@RequestBody BetDto betDto, Authentication authentication) {
        try {
            Bet bet = betServiceImp.createBet(authentication.getPrincipal().toString(),betDto.getPoint(),betDto.getMatch(),betDto.getTeam());
            return ResponseEntity.ok(bet);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // get bet by id rest api
    @GetMapping("/bets/{id}")
    public ResponseEntity<?> getBetById(@PathVariable Long id) {
        try{
        Bet bet = betServiceImp.findById(id);
        return ResponseEntity.ok(bet);
        } catch (Exception e) {
            return new ResponseEntity<>("Bet not found", HttpStatus.BAD_REQUEST);
        }
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