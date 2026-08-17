package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.auth.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "ANAMNESIS_PATIENT" )
public class AnamnesisPatient {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_ANAMNESIS_PATIENT" )
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_PATIENT" )
	private Patient patient;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_USER_THERAPIST" )
	private User therapist;

	@Column( name = "DT_ANAMNESIS_PATIENT_CREATED_DATE", columnDefinition = "TIMESTAMP DEFAULT NOW()", nullable = false )
	private LocalDateTime createdDate;

	@Column( name = "TX_ANAMNESIS_PATIENT", columnDefinition = "TEXT" )
	private String anamnesiText;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "anamnesisPatient" )
	private List<AnamnesisPatientAttachment> attachments;

	public AnamnesisPatient( Long id ) {
		this.id = id;
	}
}
