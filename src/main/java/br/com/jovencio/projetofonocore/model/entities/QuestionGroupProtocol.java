package br.com.jovencio.projetofonocore.model.entities;

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
@Table( name = "QUESTION_GROUPS_PROTOCOL" )
public class QuestionGroupProtocol {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_QUESTION_GROUP_PROTOCOL" )
	private Long id;

	@Column( name = "CD_QUESTION_GROUP_PROTOCOL_KEY", columnDefinition = "UUID DEFAULT gen_random_uuid()", nullable = false )
	private UUID key;

	@Column( name = "NM_QUESTION_GROUP_PROTOCOL", columnDefinition = "VARCHAR(120)", nullable = false )
	private String name;

	@Column( name = "NR_QUESTION_GROUP_PROTOCOL_ORDER_VIEW", columnDefinition = "BIGINT" )
	private Long orderView;

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "M2M_QUESTIONGROUPSPROTOCOL_QUESTIONSPROTOCOL",
			joinColumns = @JoinColumn( name = "ID_QUESTION_GROUP_PROTOCOL" ),
			inverseJoinColumns = @JoinColumn( name = "ID_QUESTION_PROTOCOL" ) )
	private List<QuestionProtocol> questions;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "questionGroupProtocol" )
	private List<BaseResultQuestionGroupProtocol> baseResults;

	@ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	@JoinTable( name = "M2M_QUESTIONGROUPSPROTOCOL_FUNCTIONSCALCULATOR",
			joinColumns = @JoinColumn( name = "ID_QUESTION_GROUP_PROTOCOL" ),
			inverseJoinColumns = @JoinColumn( name = "ID_FUNCTION_CALCULATOR" ) )
	private List<FunctionCalculator> functions;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_PROTOCOL", nullable = false )
	private Protocol protocol;

	public QuestionGroupProtocol( Long id ) {
		this.id = id;
	}

}
