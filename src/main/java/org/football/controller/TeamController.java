package org.football.controller;

import org.football.dto.TeamDto;
import org.football.model.Team;
import org.football.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class TeamController {
    @Autowired
    private TeamService teamService;

    // get all teams
    @GetMapping("/teams")
    public List<Team> getAllTeams() {
        return teamService.findAll();
    }

    // create team rest api
    @PostMapping("/teams")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createteam(@RequestBody TeamDto teamDto) {
        try {
            Team team = teamService.create(teamDto.getName(), teamDto.getStadium(), teamDto.getCity());
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // get team by id rest api
    @GetMapping("/teams/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Long id) {
        try {
            Team team = teamService.findById(id);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // update team rest api

    @PutMapping("/teams/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTeam(@PathVariable Long id, @RequestBody Team teamDetails) {
        try {
            Team team = teamService.findById(id);
            team.setName(teamDetails.getName());
            team.setCity(teamDetails.getCity());
            team.setStadium(teamDetails.getStadium());
            Team updatedTeam = teamService.save(team);
            return ResponseEntity.ok(updatedTeam);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // delete team rest api
    @DeleteMapping("/teams/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        try {
            teamService.delete(id);
            return ResponseEntity.ok("Team deleted");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

