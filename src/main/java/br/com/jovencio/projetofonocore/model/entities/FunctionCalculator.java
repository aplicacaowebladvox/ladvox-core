package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.enums.FunctionCalculatorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "FUNCTIONS_CALCULATOR" )
public class FunctionCalculator {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_FUNCTION_CALCULATOR" )
	private Long id;

	@Column( name = "NM_FUNCTION_CALCULATOR", columnDefinition = "VARCHAR(120)" )
	private String name;

	@Enumerated( EnumType.STRING )
	@Column( name = "CD_FUNCTION_CALCULATOR_TYPE", columnDefinition = "VARCHAR(25)" )
	private FunctionCalculatorType type;

	@Column( name = "TX_FUNCTION_CALCULATOR", columnDefinition = "TEXT", nullable = false )
	private String functionText;

}
