package br.com.jovencio.projetofonocore.model.grid.options;

import br.com.jovencio.projetofonocore.model.dtos.BaseGridOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnamnesisPatientGridOptions extends BaseGridOptions {

	private Long id;
	private Long patientId;
	private Long therapistId;
	private LocalDate createdDateStarts;
	private LocalDate createdDateEnds;

}
