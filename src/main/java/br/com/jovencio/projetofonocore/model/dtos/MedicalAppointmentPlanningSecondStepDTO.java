package br.com.jovencio.projetofonocore.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalAppointmentPlanningSecondStepDTO {

	private Long id;
	private String name;
	private List<TeamMedicalAppointmentPlanningDTO> teams;
	private boolean nextStepIsEnable;
}
