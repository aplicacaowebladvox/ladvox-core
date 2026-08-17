package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.auth.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "TEAMS_MEDICAL_APPOINTMENT_PLANNING" )
public class TeamMedicalAppointmentPlanning {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_TEAM_MEDICAL_APPOINTMENT_PLANNING" )
	private Long id;

	@Column( name = "NM_TEAM_MEDICAL_APPOINTMENT_PLANNING", columnDefinition = "VARCHAR(120)", nullable = false )
	private String name;

	@Column( name = "CD_TEAM_MEDICAL_APPOINTMENT_PLANNING_HEX_COLOR", columnDefinition = "VARCHAR(9)", nullable = false )
	private String color;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_MEDICAL_APPOINTMENT_PLANNING", nullable = false )
	private MedicalAppointmentPlanning medicalAppointmentPlanning;

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "M2M_TEAMSMEDICALAPPOINTMENTPLANNING_USERS",
			joinColumns = @JoinColumn( name = "ID_TEAM_MEDICAL_APPOINTMENT_PLANNING" ),
			inverseJoinColumns = @JoinColumn( name = "ID_USER" ) )
	private List<User> therapists;

	public TeamMedicalAppointmentPlanning( Long id ) {
		this.id = id;
	}
}
