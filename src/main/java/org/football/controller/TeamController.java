package org.football.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.football.exception.ResourceNotFoundException;
import org.football.model.Team;
import org.football.repository.TeamRepository;

@RestController
@RequestMapping("/api/")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    // get all teams
    @GetMapping("/teams")
    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    // create team rest api
    @PostMapping("/teams")
    public Team createteam(@RequestBody Team team) {
        return teamRepository.save(team);
    }

    // get team by id rest api
    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("team not exist with id :" + id));
        return ResponseEntity.ok(team);
    }

    // update team rest api

    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team teamDetails){
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("team not exist with id :" + id));

        team.setName(teamDetails.getName());
        team.setCity(teamDetails.getCity());
        team.setStadium(teamDetails.getStadium());

        Team updatedTeam = teamRepository.save(team);
        return ResponseEntity.ok(updatedTeam);
    }

    // delete team rest api
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTeam(@PathVariable Long id){
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("team not exist with id :" + id));

        teamRepository.delete(team);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}