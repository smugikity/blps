package org.football.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.football.dto.TeamDto;
import org.football.jaas.CustomJaasPrincipal;
import org.football.model.XmlUserWrapper;
import org.football.repository.XmlUserRepository;
import org.football.service.TeamService;
import org.football.service.imp.TeamServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.jaas.JaasAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private TeamServiceImp teamServiceImp;
    @Autowired
    private XmlUserRepository xmlUserRepository;

    // get all teams
    @GetMapping("/teams")
    public List<Team> getAllTeams(){
        return teamServiceImp.findAll();
    }

    // create team rest api
    @PostMapping("/teams")
    public ResponseEntity<?> createteam(@RequestBody TeamDto teamDto) {
        try {
            Team team = teamServiceImp.create(teamDto.getName(),teamDto.getStadium(),teamDto.getCity());
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
    public ResponseEntity<?> deleteTeam(@PathVariable Long id){
        try {
            teamServiceImp.delete(id);
            return ResponseEntity.ok("Team deleted");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

