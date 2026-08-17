package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "PROTOCOLS_THERAPEUTIC_PLAN" )
public class ProtocolTherapeuticPlan {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_PROTOCOL_THERAPEUTIC_PLAN" )
	private Long id;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_PROTOCOL", nullable = false )
	private Protocol protocol;

	@Column( name = "DT_PROTOCOL_THERAPEUTIC_PLAN_REQUEST", columnDefinition = "TIMESTAMP", nullable = false )
	private LocalDateTime requestDate;

	@Column( name = "DT_PROTOCOL_THERAPEUTIC_PLAN_ANSWERED", columnDefinition = "TIMESTAMP" )
	private LocalDateTime answeredDate;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "protocolTherapeuticPlan" )
	private List<AnswerQuestionProtocol> answers;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_THERAPEUTIC_PLAN", nullable = false )
	private TherapeuticPlan therapeuticPlan;

	public ProtocolTherapeuticPlan( Long id ) {
		this.id = id;
	}
}
