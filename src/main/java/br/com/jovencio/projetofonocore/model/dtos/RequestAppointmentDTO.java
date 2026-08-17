package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RequestAppointmentDTO {

	private Long id;
	private String name;
	private String email;
	private String phone1;
	private String phone2;
	private String problemDescription;
	private LocalDateTime createdDate;
	private LocalDateTime conclusionDate;
	private UUID userOfConclusionId;
	private String userOfConclusionName;

}
