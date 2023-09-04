package org.football.controller;

import org.football.exception.ResourceNotFoundException;
import org.football.model.Match;
import org.football.model.Bet;
import org.football.dto.MatchDto;
import org.football.dto.MatchScoreDto;
import org.football.model.Team;
import org.football.repository.BetRepository;
import org.football.repository.TeamRepository;
import org.football.repository.MatchRepository;
import org.football.repository.UserRepository;
import org.football.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchService matchService;

    // get all teams
    @GetMapping("/matches")
    public List<Match> getAllMatches(){
        return matchRepository.findAll();
    }

    // create team rest api
    @PostMapping("/matches")
    public ResponseEntity<Match> createMatch(@RequestBody MatchDto matchDto) {
        Match match = new Match();
        match.setName(matchDto.getName());
        match.setTeam1(teamRepository.findById(matchDto.getTeam1()).orElseThrow(() -> new ResourceNotFoundException("team not exist with id")));
        match.setTeam2(teamRepository.findById(matchDto.getTeam2()).orElseThrow(() -> new ResourceNotFoundException("team not exist with id")));
        matchRepository.save(match);
        return ResponseEntity.ok(match);
    }

    // get team by id rest api
    @GetMapping("/matches/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("match not exist with id :" + id));
        return ResponseEntity.ok(match);
    }

    // update team rest api

    @PatchMapping("/matches/{id}")
    public ResponseEntity<Match> updateMatch(@PathVariable Long id, @RequestBody MatchScoreDto matchScoreDto){
        return ResponseEntity.ok(matchService.updateScore(id, matchScoreDto.getTeam1_score(), matchScoreDto.getTeam2_score()));
    }

    // delete team rest api
    @DeleteMapping("/matches/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteMatch(@PathVariable Long id){
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("match not exist with id :" + id));

        matchRepository.delete(match);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}