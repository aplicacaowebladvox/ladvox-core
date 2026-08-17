package br.com.jovencio.projetofonocore.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalAppointmentPlanningFirstStepDTO {

	private Long id;
	private String name;
	private LocalDate initialValidity;
	private LocalDate finalValidity;
	private boolean nextStepIsEnable;

}
