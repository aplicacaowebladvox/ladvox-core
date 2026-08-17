package br.com.jovencio.projetofonocore.model.grid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestAppointmentGrid {

	private Long id;
	private String name;
	private String email;
	private String phone1;
	private String phone2;
	private LocalDateTime createdDate;
	private LocalDateTime conclusionDate;
	private UUID userOfConclusionId;
	private String userOfConclusionName;
}
