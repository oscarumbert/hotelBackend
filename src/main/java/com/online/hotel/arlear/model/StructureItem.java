package com.online.hotel.arlear.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StructureItem {
	private String section;
	private String name;
	private Double value;
	private String date;
}
