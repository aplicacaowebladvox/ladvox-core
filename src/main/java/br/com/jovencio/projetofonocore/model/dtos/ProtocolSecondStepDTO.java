package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProtocolSecondStepDTO {

	private Long id;
	private UUID key;
	private List<AnswerTypeProtocolDTO> answersType;
	private boolean nextStepIsEnable;
}
