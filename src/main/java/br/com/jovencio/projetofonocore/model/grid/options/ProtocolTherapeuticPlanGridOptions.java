package br.com.jovencio.projetofonocore.model.grid.options;

import br.com.jovencio.projetofonocore.model.dtos.BaseGridOptions;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProtocolTherapeuticPlanGridOptions extends BaseGridOptions {

	private Long id;
	private Long therapeuticPlanId;
	private Long patientId;
	private Long protocolId;
	private LocalDate requestDateStarts;
	private LocalDate requestDateEnds;
	private LocalDate answeredDateStarts;
	private LocalDate answeredDateEnds;
}

