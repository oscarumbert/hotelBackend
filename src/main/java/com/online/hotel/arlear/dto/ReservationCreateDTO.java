package com.online.hotel.arlear.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;

import lombok.Data;

@Data
public class ReservationCreateDTO {

	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate beginDate;
	//private String beginDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate endDate;
	//private String endDate;
	@NotNull
	private String adultsCuantity;
	private String childsCuantity;
	//private ReservationType reservationType;
	private String reservationType;
	//private RoomAditionals aditionals;
	private String aditionals;
	private ContactDTO contact;//(relacion contacto asociado)
	private String price;
	private String sign;//seña?
}
