package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "ARTICLES" )
public class Article {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_ARTICLE" )
	private Long id;

	@Column( name = "TX_ARTICLE", columnDefinition = "TEXT", nullable = false )
	private String article;

	@Column( name = "TX_ARTICLE_TITLE", columnDefinition = "VARCHAR(255)", nullable = false )
	private String title;

}
