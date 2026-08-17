package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MyProtocolDTO {

	private Long id;
	private String name;
	private String abbreviation;
	private String description;
	private List<AnswerTypeProtocolDTO> answersType;
	private List<QuestionProtocolDTO> questions;
}
