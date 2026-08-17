package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.enums.WeekDayEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class TherapeuticPlanBaseDataDTO {

	private Long id;
	private Long medicalAppointmentId;
	private String medicalAppointmentRoom;
	private WeekDayEnum medicalAppointmentWeekDay;
	private LocalTime medicalAppointmentHour;
	private Long medicalAppointmentPatientId;
	private String medicalAppointmentPatientName;
	private Long medicalAppointmentMedicalAppointmentPlanningId;
	private String medicalAppointmentMedicalAppointmentPlanningName;
}
