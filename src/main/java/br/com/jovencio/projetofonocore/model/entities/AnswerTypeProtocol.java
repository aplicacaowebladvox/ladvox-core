package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.enums.AnswerValueTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "ANSWER_TYPES_PROTOCOL" )
public class AnswerTypeProtocol {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_ANSWER_TYPE_PROTOCOL" )
	private Long id;

	@Column( name = "CD_ANSWER_TYPE_PROTOCOL_KEY", columnDefinition = "UUID DEFAULT gen_random_uuid()", nullable = false )
	private UUID key;

	@Column( name = "NR_ANSWER_TYPE_PROTOCOL_SEQUENCE", columnDefinition = "INT DEFAULT 0", nullable = false )
	private int sequence;

	@Column( name = "NM_ANSWER_TYPE_PROTOCOL_SEQUENCE", columnDefinition = "VARCHAR(120)", nullable = false )
	private String name;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "answerTypeProtocol" )
	private List<AcceptedValueAnswerTypeProtocol> acceptableValues;

	@Column( name = "BL_ANSWER_TYPE_PROTOCOL_USE_RANGE_ANSWER", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false )
	private boolean useRangeAnswer;

	@Enumerated( EnumType.STRING )
	@Column( name = "CD_ANSWER_TYPE_PROTOCOL_ANSWER_VALUE_TYPE", columnDefinition = "VARCHAR(7)", nullable = false )
	private AnswerValueTypeEnum answerValueType;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_PROTOCOL" )
	private Protocol protocol;

	public AnswerTypeProtocol( Long id ) {
		this.id = id;
	}
}
