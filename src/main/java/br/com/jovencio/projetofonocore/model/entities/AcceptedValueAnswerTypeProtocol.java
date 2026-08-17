package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table( name = "ACCEPTED_VALUES_ANSWER_TYPE_PROTOCOL" )
public class AcceptedValueAnswerTypeProtocol {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_ACCEPTED_VALUE_ANSWER_TYPE_PROTOCOL" )
	private Long id;

	@Column( name = "CD_ACCEPTED_VALUE_ANSWER_TYPE_PROTOCOL_KEY", columnDefinition = "UUID DEFAULT gen_random_uuid() NOT NULL" )
	private UUID key;

	@Column( name = "NR_ACCEPTED_VALUE_ANSWER_TYPE_PROTOCOL_VALUE" )
	private Integer value;

	@Column( name = "TX_ACCEPTED_VALUE_ANSWER_TYPE_PROTOCOL_PRESENTATION", columnDefinition = "VARCHAR(120)")
	private String presentation;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_ANSWER_TYPE_PROTOCOL" )
	private AnswerTypeProtocol answerTypeProtocol;
}
