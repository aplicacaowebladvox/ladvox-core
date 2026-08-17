package br.com.jovencio.projetofonocore.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolCalculatedGroupFunctionEvaluatedDTO {
	private String functionTextEvaluated;
	private Long answersTypeId;
	private Double value;
}
