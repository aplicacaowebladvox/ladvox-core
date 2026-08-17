package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.enums.AnswerValueTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AnswerQuestionProtocolDTO {

	private Long id;
	private UUID key;
	private Long answerTypeId;
	private Double answer;
	private AnswerValueTypeEnum answerValueType;
	private Long questionId;
	private Long protocolTherapeuticPlanId;
}
