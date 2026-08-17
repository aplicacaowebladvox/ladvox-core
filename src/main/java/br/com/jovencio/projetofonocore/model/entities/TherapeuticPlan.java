package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "THERAPEUTIC_PLANS" )
public class TherapeuticPlan {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_THERAPEUTIC_PLAN" )
	private Long id;

	@Column( name = "TX_THERAPEUTIC_PLAN", columnDefinition = "TEXT" )
	private String therapeuticTextPlan;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "therapeuticPlan" )
	private List<ProtocolTherapeuticPlan> protocols;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_MEDICAL_APPOINTMENT", nullable = false )
	private MedicalAppointment medicalAppointment;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "therapeuticPlan" )
	private List<TherapeuticPlanEditorHistory> editorsHistory;

	public TherapeuticPlanEditorHistory getEditor() {
		return Optional.ofNullable( editorsHistory ).orElse( Collections.emptyList() ).stream().filter( tpeh -> tpeh.getEndedDate() == null ).findFirst().orElse( null );
	}

	public TherapeuticPlan( Long id ) {
		this.id = id;
	}
}
