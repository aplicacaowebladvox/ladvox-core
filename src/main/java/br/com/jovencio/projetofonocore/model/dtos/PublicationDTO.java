package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicationDTO {

	private Long id;
	private String title;
	private String bibliographicReference;
	private String publicationAbstract;

}
