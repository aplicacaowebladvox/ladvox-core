package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.enums.AnswerValueTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table( name = "ANSWERS_QUESTION_PROTOCOL" )
public class AnswerQuestionProtocol {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_ANSWER_QUESTION_PROTOCOL" )
	private Long id;

	@Column( name = "CD_ANSWER_QUESTION_PROTOCOL_KEY", columnDefinition = "UUID DEFAULT gen_random_uuid()", nullable = false )
	private UUID key;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_ANSWER_TYPE_PROTOCOL" )
	private AnswerTypeProtocol answerType;

	@Column( name = "NR_ANSWER_QUESTION_PROTOCOL", columnDefinition = "NUMERIC(19, 2)", nullable = false )
	private Double answer;

	@Enumerated( EnumType.STRING )
	@Column( name = "CD_ANSWER_QUESTION_PROTOCOL_ANSWER_VALUE_TYPE", columnDefinition = "VARCHAR(7)", nullable = false )
	private AnswerValueTypeEnum answerValueType;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_QUESTION_PROTOCOL" )
	private QuestionProtocol question;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_PROTOCOL_THERAPEUTIC_PLAN" )
	private ProtocolTherapeuticPlan protocolTherapeuticPlan;

}
