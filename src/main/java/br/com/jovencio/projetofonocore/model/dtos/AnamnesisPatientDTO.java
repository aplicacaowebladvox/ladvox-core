package br.com.jovencio.projetofonocore.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnamnesisPatientDTO {

	private Long id;
	private Long patientId;
	private UUID therapistId;
	private LocalDateTime createdDate;
	private String anamnesiText;
}
