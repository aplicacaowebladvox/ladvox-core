package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {

	private Long id;
	private String title;
	private String status;
	private String type;
	private String description;

}
