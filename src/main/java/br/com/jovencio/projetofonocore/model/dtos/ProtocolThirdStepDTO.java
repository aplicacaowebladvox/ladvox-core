package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProtocolThirdStepDTO {

	private Long id;
	private UUID key;
	private List<QuestionProtocolDTO> questions;
	private boolean nextStepIsEnable;

}
