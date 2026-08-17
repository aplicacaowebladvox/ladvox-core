package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProtocolTherapeuticPlanDTO {

	private Long id;
	private Long protocolId;
	private LocalDateTime requestDate;
	private LocalDateTime answeredDate;
	private List<AnswerQuestionProtocolDTO> answers;
	private Long therapeuticPlanId;
}
