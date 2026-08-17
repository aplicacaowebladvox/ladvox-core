package br.com.jovencio.projetofonocore.model.grid;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProtocolGrid {

	private Long id;
	private String name;
	private String abbreviation;
	private LocalDate initialValidity;
	private LocalDate finalValidity;

	/*
	 * br.com.jovencio.projetofonocore.dao.repositories.IProjectRepository.search
	 */
	public ProtocolGrid(
			Long id,
			String name,
			String abbreviation,
			LocalDate initialValidity,
			LocalDate finalValidity
	) {
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
		this.initialValidity = initialValidity;
		this.finalValidity = finalValidity;
	}

}
