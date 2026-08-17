package br.com.jovencio.projetofonocore.model.grid.options;

import br.com.jovencio.projetofonocore.model.dtos.BaseGridOptions;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicalAppointmentPlanningGridOptions extends BaseGridOptions {

	private Long id;
	private String name;
	private LocalDate initialValidityStarts;
	private LocalDate initialValidityEnds;
	private LocalDate finalValidityStarts;
	private LocalDate finalValidityEnds;
	private String teamName;
	private Long patientId;
	private String room;
}
