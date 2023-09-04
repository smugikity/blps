package org.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.football.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

}