package org.football.dto;

import lombok.Data;

@Data
public class BetDto {
    private int point;
    private long match;
    private long team;
}