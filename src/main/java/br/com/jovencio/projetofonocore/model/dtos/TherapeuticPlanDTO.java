package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TherapeuticPlanDTO {

	private Long id;
	private String therapeuticTextPlan;
	private List<Long> protocolsIds;
	private Long medicalAppointmentId;

	public TherapeuticPlanDTO( Long id ) {
		this.id = id;
	}
}
