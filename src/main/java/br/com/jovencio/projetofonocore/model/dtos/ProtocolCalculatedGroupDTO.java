package br.com.jovencio.projetofonocore.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolCalculatedGroupDTO {

	private long groupId;
	private String groupName;
	private List<BaseResultQuestionGroupProtocolDTO> groupBaseResults;
	private List<ProtocolCalculatedGroupFunctionDTO> groupFunctions;
}
