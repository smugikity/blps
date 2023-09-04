package org.football.dto;

import lombok.Data;

@Data
public class MatchDto {
    private String name;
    private long team1;
    private long team2;
}