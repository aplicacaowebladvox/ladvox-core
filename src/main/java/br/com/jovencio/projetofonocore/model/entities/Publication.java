package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "PUBLICATIONS" )
public class Publication {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_PUBLICATION" )
	private Long id;

	@Column( name = "TX_PUBLICATION_TITLE", columnDefinition = "VARCHAR(255)", nullable = false )
	private String title;

	@Column( name = "TX_PUBLICATION_BIBLIOGRAPHIC_REFERENCE", columnDefinition = "VARCHAR(255)", nullable = false )
	private String bibliographicReference;

	@Column( name = "TX_PUBLICATION_ABSTRACT", columnDefinition = "TEXT", nullable = false )
	private String publicationAbstract;

}
