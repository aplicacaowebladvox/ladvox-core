package br.com.jovencio.projetofonocore.model.grid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalAppointmentPlanningGrid {

	private Long id;
	private String name;
	private LocalDate initialValidity;
	private LocalDate finalValidity;
}
