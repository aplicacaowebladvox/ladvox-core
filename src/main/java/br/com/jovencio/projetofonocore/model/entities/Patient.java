package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.auth.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "PATIENTS" )
public class Patient {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_PATIENT" )
	private Long id;

	@Column( name = "NM_PATIENT", columnDefinition = "VARCHAR(120)", nullable = false )
	private String name;

	@Column( name = "CD_PATIENT_DOCUMENT", columnDefinition = "VARCHAR(11)", nullable = false )
	private String document;

	@Column( name = "CD_PATIENT_NATIONAL_HEALTH_CARD", columnDefinition = "VARCHAR(25)", nullable = false )
	private String nationalHealthCard;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "ID_GENDER" )
	private Gender gender;

	@Column( name = "TX_PATIENT_OTHER_GENDER_DEFINITION", columnDefinition = "VARCHAR(50)" )
	private String otherGenderDefinition;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "ID_EDUCATION" )
	private Education education;

	@Column( name = "TX_PATIENT_OTHER_EDUCATION_DEFINITION", columnDefinition = "VARCHAR(50)" )
	private String otherEducationDefinition;

	@Column( name = "TX_PATIENT_PROFESSION", columnDefinition = "VARCHAR(150)" )
	private String profession;

	@Column( name = "CD_PATIENT_MEDICAL_RECORD", columnDefinition = "VARCHAR(50)" )
	private String medicalRecord;

	@Column( name = "DT_PATIENT_BIRH_DATE", columnDefinition = "DATE", nullable = false )
	private LocalDate birthDate;

	@Column( name = "CD_PATIENT_MAIN_PHONE", columnDefinition = "VARCHAR(15)" )
	private String mainPhone;

	@Column( name = "CD_PATIENT_SECUNDARY_PHONE", columnDefinition = "VARCHAR(15)" )
	private String phoneSecundary;

	@Column( name = "TX_PATIENT_PHONE_OBSERVATION", columnDefinition = "VARCHAR(500)" )
	private String phoneObservation;

	@Column( name = "TX_PATIENT_MAIN_MAIL", columnDefinition = "VARCHAR(255)" )
	private String mainMail;

	@Column( name = "TX_PATIENT_MAIL_SECUNDARY", columnDefinition = "VARCHAR(255)" )
	private String mailSecundary;

	@Column( name = "TX_PATIENT_MAIL_OBSERVATION", columnDefinition = "VARCHAR(500)" )
	private String mailObservation;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "ID_ADDRESS" )
	private Address address;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "patient" )
	private List<AnamnesisPatient> anamnesis;

	@OneToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "ID_USER" )
	private User user;

	public Patient( Long id ) {
		this.id = id;
	}
}
