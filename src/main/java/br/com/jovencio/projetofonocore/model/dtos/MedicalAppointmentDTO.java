package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.enums.WeekDayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalAppointmentDTO {

	private Long id;
	private Long teamId;
	private String room;
	private Long patientId;
	private WeekDayEnum weekDay;
	private LocalTime hour;
	private Long medicalAppointmentPlanningId;
	private String observation;
}
