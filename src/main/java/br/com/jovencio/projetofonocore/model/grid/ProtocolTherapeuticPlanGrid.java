package br.com.jovencio.projetofonocore.model.grid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProtocolTherapeuticPlanGrid {

	private Long id;
	private Long protocolId;
	private String protocolDescription;
	private LocalDateTime requestDate;
	private LocalDateTime answeredDate;
	private List<String> resultsDescription;
	private Long therapeuticPlanId;
	private UUID currentUserEditorId;

	public ProtocolTherapeuticPlanGrid(
			Long id,
			Long protocolId,
			String protocolDescription,
			LocalDateTime requestDate,
			LocalDateTime answeredDate,
			Long therapeuticPlanId
	) {
		this.id = id;
		this.protocolId = protocolId;
		this.protocolDescription = protocolDescription;
		this.requestDate = requestDate;
		this.answeredDate = answeredDate;
		this.therapeuticPlanId = therapeuticPlanId;
	}
}
