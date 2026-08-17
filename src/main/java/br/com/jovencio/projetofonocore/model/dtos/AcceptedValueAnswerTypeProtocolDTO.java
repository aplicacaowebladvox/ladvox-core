package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AcceptedValueAnswerTypeProtocolDTO {

	private Long id;
	private UUID key;
	private Integer value;
	private String presentation;
	private Long answerTypeProtocolId;

}
