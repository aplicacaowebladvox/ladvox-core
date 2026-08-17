package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.enums.AnswerValueTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BaseResultQuestionGroupProtocolDTO {

	private Long id;
	private UUID key;
	private String name;
	private Boolean isRange;
	private Double initialValue;
	private Double finalValue;
	private AnswerValueTypeEnum answerValueType;
	private Long questionGroupProtocolId;

}
