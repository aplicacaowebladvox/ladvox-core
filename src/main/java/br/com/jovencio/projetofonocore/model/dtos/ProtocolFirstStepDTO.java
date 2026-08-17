package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ProtocolFirstStepDTO {

	private Long id;
	private UUID key;
	private String name;
	private String abbreviation;
	private String description;
	private LocalDate initialValidity;
	private LocalDate finalValidity;

}
