package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "SOCIAL_MEDIAS" )
public class SocialMedia {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_SOCIAL_MEDIA" )
	private Long id;

	@Column( name = "TX_SOCIAL_MEDIA_ICON_CLASS", columnDefinition = "VARCHAR(125)", nullable = false )
	private String iconClass;

	@Column( name = "CD_SOCIAL_MEDIA_HEX_COLOR", columnDefinition = "VARCHAR(9)" )
	private String iconColor;

	@Column( name = "TX_SOCIAL_MEDIA_TITLE", columnDefinition = "VARCHAR(125)", nullable = false )
	private String title;

	@Column( name = "TX_SOCIAL_MEDIA_URL", columnDefinition = "VARCHAR(255)", nullable = false )
	private String url;

}
