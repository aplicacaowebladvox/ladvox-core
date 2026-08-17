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
@Table( name = "ANAMNESIS_PATIENT_ATTACHMENTS" )
public class AnamnesisPatientAttachment {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_ANAMNESIS_PATIENT_ATTACHMENT" )
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_ANAMNESIS_PATIENT" )
	private AnamnesisPatient anamnesisPatient;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_ATTACHMENT" )
	private Attachment attachment;

}
