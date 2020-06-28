package com.online.hotel.arlear.util;

import java.time.LocalDate;

import lombok.Data;

@Data
public class StructureReport {
	private String date;
	private String category;
	private int egressCount;
	private int entryCount;
	private boolean duplicate;
	private Integer valueTotal;
	private String agrupation;
}
