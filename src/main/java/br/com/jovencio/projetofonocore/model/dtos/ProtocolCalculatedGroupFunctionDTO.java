package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.enums.FunctionCalculatorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolCalculatedGroupFunctionDTO {

	private long id;
	private String functionText;
	private String name;
	private FunctionCalculatorType type;
	private List<ProtocolCalculatedGroupFunctionEvaluatedDTO> values;

}
