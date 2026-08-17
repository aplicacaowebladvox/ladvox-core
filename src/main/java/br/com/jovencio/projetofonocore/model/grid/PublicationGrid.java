package br.com.jovencio.projetofonocore.model.grid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicationGrid {

	private Long id;
	private String title;
	private String bibliographicReference;

	/*
	 * br.com.jovencio.projetofonocore.dao.repositories.IPublicationRepository.search
	 */
	public PublicationGrid(
			Long id,
			String title,
			String bibliographicReference
	) {
		this.id = id;
		this.title = title;
		this.bibliographicReference = bibliographicReference;
	}
}
