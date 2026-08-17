package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.entities.AcceptedValueAnswerTypeProtocol;
import br.com.jovencio.projetofonocore.model.enums.AnswerValueTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AnswerTypeProtocolDTO {

	private Long id;
	private UUID key;
	private int sequence;
	private String name;
	private List<AcceptedValueAnswerTypeProtocolDTO> acceptableValues;
	private boolean useRangeAnswer;
	private AnswerValueTypeEnum answerValueType;
}
