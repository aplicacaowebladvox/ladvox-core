package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "MEDICAL_APPOINTMENT_PLANNINGS" )
public class MedicalAppointmentPlanning {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_MEDICAL_APPOINTMENT_PLANNING" )
	private Long id;

	@Column( name = "NM_MEDICAL_APPOINTMENT_PLANNING", columnDefinition = "VARCHAR(120)", nullable = false )
	private String name;

	@Column( name = "DT_MEDICAL_APPOINTMENT_PLANNING_INITIAL_VALIDITY", columnDefinition = "DATE", nullable = false )
	private LocalDate initialValidity;

	@Column( name = "DT_MEDICAL_APPOINTMENT_PLANNING_FINAL_VALIDITY", columnDefinition = "DATE" )
	private LocalDate finalValidity;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "medicalAppointmentPlanning" )
	private List<TeamMedicalAppointmentPlanning> teams;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "medicalAppointmentPlanning" )
	private List<MedicalAppointment> medicalAppointments;

	public MedicalAppointmentPlanning( Long id ) {
		this.id = id;
	}
}
