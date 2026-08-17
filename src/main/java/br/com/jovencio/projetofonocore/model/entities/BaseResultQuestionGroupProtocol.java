package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.enums.AnswerValueTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table( name = "BASE_RESULTS_QUESTION_GROUP_PROTOCOL" )
public class BaseResultQuestionGroupProtocol {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_BASE_RESULT_QUESTION_GROUP_PROTOCOL" )
	private Long id;

	@Column( name = "CD_BASE_RESULT_QUESTION_GROUP_PROTOCOL_KEY", columnDefinition = "UUID DEFAULT gen_random_uuid()", nullable = false )
	private UUID key;

	@Column( name = "NM_BASE_RESULT_QUESTION_GROUP_PROTOCOL", columnDefinition = "VARCHAR(120)", nullable = false )
	private String name;

	@Column( name = "BL_BASE_RESULT_QUESTION_GROUP_PROTOCOL_IS_RANGE", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false )
	private boolean isRange;

	@Column( name = "NR_BASE_RESULT_QUESTION_GROUP_PROTOCOL_INITIAL_VALUE", columnDefinition = "NUMERIC(19,2) DEFAULT 0.0", nullable = false )
	private Double initialValue;

	@Column( name = "NR_BASE_RESULT_QUESTION_GROUP_PROTOCOL_FINAL_VALUE", columnDefinition = "NUMERIC(19,2)" )
	private Double finalValue;

	@Enumerated( EnumType.STRING )
	@Column( name = "CD_BASE_RESULT_QUESTION_GROUP_PROTOCOL_ANSWER_VALUE_TYPE", columnDefinition = "VARCHAR(7)", nullable = false )
	private AnswerValueTypeEnum answerValueType;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_QUESTION_GROUP_PROTOCOL" )
	private QuestionGroupProtocol questionGroupProtocol;

}
