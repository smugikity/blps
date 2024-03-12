package org.football.service.imp;

import org.football.exception.ResourceNotFoundException;
import org.football.model.Team;
import org.football.repository.TeamRepository;
import org.football.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class TeamServiceImp implements TeamService {
    @Autowired
    TeamRepository teamRepository;

    @Override
    public Team findById(Long id) throws Exception {
        return teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Team create(String name, String stadium, String city) throws Exception {
        Team team = new Team();
        team.setName(name);
        team.setStadium(stadium);
        team.setCity(city);
        team = teamRepository.save(team);
        return team;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) throws Exception {
        Team match = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tem not exist with id :" + id));
        teamRepository.delete(match);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }
}
