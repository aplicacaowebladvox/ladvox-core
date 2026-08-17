package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "PROJECTS" )
public class Project {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_PROJECT" )
	private Long id;

	@Column( name = "TX_PROJECT_TITLE", columnDefinition = "VARCHAR(255)", nullable = false )
	private String title;

	@Column( name = "TX_PROJECT_STATUS", columnDefinition = "VARCHAR(125)", nullable = false )
	private String status;

	@Column( name = "TX_PROJECT_TYPE", columnDefinition = "VARCHAR(125)" )
	private String type;

	@Column( name = "TX_PROJECT_DESCRIPTION", columnDefinition = "TEXT", nullable = false )
	private String description;

}
