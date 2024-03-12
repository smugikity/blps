package org.football.controller;

import org.football.dto.MatchDto;
import org.football.dto.MatchScoreDto;
import org.football.model.Match;
import org.football.service.MatchService;
import org.football.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    // get all teams
    @GetMapping("/matches")
    public List<Match> getAllMatches() {
        return matchService.findAll();
    }

    // create team rest api
    @PostMapping("/matches")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createMatch(@RequestBody MatchDto matchDto) {
        try {
            Match match = matchService.create(matchDto.getName(), matchDto.getTeam1(), matchDto.getTeam2());
            return ResponseEntity.ok(match);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // get team by id rest api
    @GetMapping("/matches/{id}")
    public ResponseEntity<?> getMatchById(@PathVariable Long id) {
        try {
            Match match = matchService.findById(id);
            return ResponseEntity.ok(match);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // update team rest api

    @PatchMapping("/matches/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMatch(@PathVariable Long id, @RequestBody MatchScoreDto matchScoreDto) {
        try {
            Match match = matchService.updateScore(id, matchScoreDto.getTeam1_score(), matchScoreDto.getTeam2_score());
            return ResponseEntity.ok(match);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // delete team rest api
    @DeleteMapping("/matches/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMatch(@PathVariable Long id) {
        try {
            matchService.delete(id);
            return ResponseEntity.ok("Match deleted");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/matches/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMatch(@PathVariable Long id, @RequestBody MatchDto matchDto) {
        try {
            Match match = matchService.findById(id);
            match.setName(matchDto.getName());
            match.setTeam1(teamService.findById(matchDto.getTeam1()));
            match.setTeam2(teamService.findById(matchDto.getTeam2()));
            Match updatedMatch = matchService.save(match);
            return ResponseEntity.ok(updatedMatch);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}