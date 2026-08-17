package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "PROTOCOLS" )
public class Protocol {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_PROTOCOL" )
	private Long id;

	@Column( name = "CD_PROTOCOL_KEY", columnDefinition = "UUID DEFAULT gen_random_uuid()", nullable = false )
	private UUID key;

	@Column( name = "NM_PROTOCOL", columnDefinition = "VARCHAR(120)", nullable = false )
	private String name;

	@Column( name = "TX_PROTOCOL_ABBREVIATION", columnDefinition = "VARCHAR(10)", nullable = false )
	private String abbreviation;

	@Column( name = "TX_PROTOCOL_DESCRIPTION", columnDefinition = "TEXT", nullable = false )
	private String description;

	@Column( name = "DT_PROTOCOL_INITIAL_VALIDITY", columnDefinition = "DATE", nullable = false )
	private LocalDate initialValidity;

	@Column( name = "DT_PROTOCOL_FINAL_VALIDITY", columnDefinition = "DATE" )
	private LocalDate finalValidity;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "protocol" )
	private List<AnswerTypeProtocol> answersType;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "protocol" )
	private List<QuestionProtocol> questions;

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "protocol" )
	private List<QuestionGroupProtocol> groups;

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "M2M_PROTOCOLS_FUNCTIONSCALCULATOR",
			joinColumns = @JoinColumn( name = "ID_PROTOCOL" ),
			inverseJoinColumns = @JoinColumn( name = "ID_FUNCTION_CALCULATOR" ) )
	private List<FunctionCalculator> functions;

	public Protocol( long id ) {
		this.id = id;
	}
}
