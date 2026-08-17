package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.enums.FunctionCalculatorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FunctionCalculatorDTO {

	private Long id;
	private String functionText;
	private String functionTextEvaluated;
	private String name;
	private FunctionCalculatorType type;
	private Double value;

}
