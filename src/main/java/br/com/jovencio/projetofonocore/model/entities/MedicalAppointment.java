package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.enums.WeekDayEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "MEDICAL_APPOINTMENTS" )
public class MedicalAppointment {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_MEDICAL_APPOINTMENT" )
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_TEAM_MEDICAL_APPOINTMENT_PLANNING" )
	private TeamMedicalAppointmentPlanning team;

	@Column( name = "CD_MEDICAL_APPOINTMENT_ROOM", columnDefinition = "VARCHAR(50)" )
	private String room;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_PATIENT", nullable = false )
	private Patient patient;

	@Enumerated( EnumType.STRING )
	@Column( name = "CD_MEDICAL_APPOINTMENT_WEEK_DAY", columnDefinition = "VARCHAR(9)" )
	private WeekDayEnum weekDay;

	@Column( name = "HR_MEDICAL_APPOINTMENT", columnDefinition = "TIME" )
	private LocalTime hour;

	@Column( name = "TX_MEDICAL_APPOINTMENT_OBSERVATION", columnDefinition = "VARCHAR(255)" )
	private String observation;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_MEDICAL_APPOINTMENT_PLANNING" )
	private MedicalAppointmentPlanning medicalAppointmentPlanning;

	public MedicalAppointment( Long id ) {
		this.id = id;
	}
}
