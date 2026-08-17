package br.com.jovencio.projetofonocore.model.grid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectGrid {

	private Long id;
	private String title;
	private String status;

	/*
	 * br.com.jovencio.projetofonocore.dao.repositories.IProjectRepository.search
	 */
	public ProjectGrid(
			Long id,
			String title,
			String status
	) {
		this.id = id;
		this.title = title;
		this.status = status;
	}
}
