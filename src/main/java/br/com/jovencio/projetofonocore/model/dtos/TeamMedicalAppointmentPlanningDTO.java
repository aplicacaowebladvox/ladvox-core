package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TeamMedicalAppointmentPlanningDTO {

	private Long id;
	private String name;
	private String color;
	private Long medicalAppointmentPlanningId;
	private List<UUID> therapistsIds;
}
