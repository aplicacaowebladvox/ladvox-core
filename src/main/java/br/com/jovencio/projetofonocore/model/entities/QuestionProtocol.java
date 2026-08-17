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
@Table( name = "QUESTIONS_PROTOCOL" )
public class QuestionProtocol {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_QUESTION_PROTOCOL" )
	private Long id;

	@Column( name = "CD_QUESTION_PROTOCOL_KEY", columnDefinition = "UUID DEFAULT gen_random_uuid()", nullable = false )
	private UUID key;

	@Column( name = "NR_QUESTION_PROTOCOL_SEQUENCE", columnDefinition = "INT DEFAULT 0", nullable = false )
	private int sequence;

	@Column( name = "TX_QUESTION_PROTOCOL", columnDefinition = "VARCHAR(255)", nullable = false )
	private String question;

	@Column( name = "TX_QUESTION_PROTOCOL_ABBREVIATION", columnDefinition = "VARCHAR(10)" )
	private String abbreviation;

	@ManyToMany( fetch = FetchType.EAGER, mappedBy = "questions" )
	private List<QuestionGroupProtocol> groups;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( name = "ID_PROTOCOL", nullable = false )
	private Protocol protocol;

	public QuestionProtocol( Long id ) {
		this.id = id;
	}
}
