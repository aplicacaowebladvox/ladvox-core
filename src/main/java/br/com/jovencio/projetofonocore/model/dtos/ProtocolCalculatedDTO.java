package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.entities.AnswerTypeProtocol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolCalculatedDTO {
	private long id;
	private String abbreviation;
	private String name;
	private long protocolTherapeuticPlanId;
	private List<AnswerTypeProtocolDTO> answersType;
	private List<ProtocolCalculatedGroupDTO> groups;
	private List<ProtocolCalculatedGroupFunctionDTO> functions;
}
