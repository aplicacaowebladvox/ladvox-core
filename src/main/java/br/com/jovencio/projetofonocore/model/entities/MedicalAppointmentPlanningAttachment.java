package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "MEDICAL_APPOINTMENT_PLANNINGS_ATTACHMENTS" )
public class MedicalAppointmentPlanningAttachment {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_MEDICAL_APPOINTMENT_PLANNING_ATTACHMENT" )
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_MEDICAL_APPOINTMENT_PLANNING" )
	private MedicalAppointmentPlanning medicalAppointmentPlanning;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_ATTACHMENT" )
	private Attachment attachment;

}
