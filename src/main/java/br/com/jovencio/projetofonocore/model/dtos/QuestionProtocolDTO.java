package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class QuestionProtocolDTO {

	private Long id;
	private UUID key;
	private int sequence;
	private String question;
	private String abbreviation;
	private Long protocolId;

}
