package org.football.controller;

import org.football.dto.TeamDto;
import org.football.model.Team;
import org.football.repository.XmlUserRepository;
import org.football.service.imp.TeamServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class TeamController {
    @Autowired
    private TeamServiceImp teamServiceImp;
    @Autowired
    private XmlUserRepository xmlUserRepository;

    // get all teams
    @GetMapping("/teams")
    public List<Team> getAllTeams() {
        return teamServiceImp.findAll();
    }

    // create team rest api
    @PostMapping("/teams")
    public ResponseEntity<?> createteam(@RequestBody TeamDto teamDto) {
        try {
            Team team = teamServiceImp.create(teamDto.getName(), teamDto.getStadium(), teamDto.getCity());
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // get team by id rest api
    @GetMapping("/teams/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Long id) {
        try {
            Team team = teamServiceImp.findById(id);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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

    // delete team rest api
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        try {
            teamServiceImp.delete(id);
            return ResponseEntity.ok("Team deleted");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

