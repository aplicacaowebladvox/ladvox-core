package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProtocolFourthStepDTO {

	private Long id;
	private UUID key;
	private List<AnswerTypeProtocolDTO> answersType;
	private List<QuestionProtocolDTO> questions;
	private List<QuestionGroupProtocolDTO> groups;
	private boolean nextStepIsEnable;

}
