package com.movieBookV2.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.movieBookV2.model.ShowStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ShowRequest {
	private LocalDate showDate;
    private LocalTime showTime;
    private String format;
    private String language;
    
}
