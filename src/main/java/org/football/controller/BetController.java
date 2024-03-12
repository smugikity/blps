package org.football.controller;

import org.football.dto.BetDto;
import org.football.model.Bet;
import org.football.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class BetController {
    @Autowired
    private BetService betService;

    // get all teams
    @GetMapping("/bets")
    public List<Bet> getAllBets() {
        return betService.findAll();
    }

    // create team rest api
    @PostMapping("/bets")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> createBet(@RequestBody BetDto betDto, Authentication authentication) {
        try {
            Bet bet = betService.createBet(authentication.getPrincipal().toString(), betDto.getPoint(), betDto.getMatch(), betDto.getTeam());
            return ResponseEntity.ok(bet);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // get bet by id rest api
    @GetMapping("/bets/{id}")
    public ResponseEntity<?> getBetById(@PathVariable Long id) {
        try {
            Bet bet = betService.findById(id);
            return ResponseEntity.ok(bet);
        } catch (Exception e) {
            return new ResponseEntity<>("Bet not found", HttpStatus.BAD_REQUEST);
        }
    }
}