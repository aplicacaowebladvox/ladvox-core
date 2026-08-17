package br.com.jovencio.projetofonocore.model.grid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AnamnesisPatientGrid {

	private Long id;
	private Long patientId;
	private String patientName;
	private UUID therapistId;
	private String therapistName;
	private LocalDateTime createdDate;

	public AnamnesisPatientGrid(
			Long id,
			Long patientId,
			String patientName,
			UUID therapistId,
			String therapistName,
			LocalDateTime createdDate
	) {
		this.patientId = patientId;
		this.id = id;
		this.patientName = patientName;
		this.therapistId = therapistId;
		this.therapistName = therapistName;
		this.createdDate = createdDate;
	}
}
