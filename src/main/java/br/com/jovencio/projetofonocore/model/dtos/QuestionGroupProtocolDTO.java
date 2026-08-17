package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class QuestionGroupProtocolDTO {

	private Long id;
	private UUID key;
	private String name;
	private Long orderView;
	private List<Long> questionsIds;
	private List<BaseResultQuestionGroupProtocolDTO> baseResults;
	private List<FunctionCalculatorDTO> functions;
	private Long protocolId;

}
