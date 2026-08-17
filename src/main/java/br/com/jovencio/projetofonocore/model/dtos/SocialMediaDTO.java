package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SocialMediaDTO {

	private Long id;
	private String iconClass;
	private String iconColor;
	private String title;
	private String url;

	/*
	 * br.com.jovencio.projetofonocore.dao.repositories.ISocialMediaRepository.search
	 */
	public SocialMediaDTO(
			long id,
			String iconClass,
			String iconColor,
			String title,
			String url
	) {
		this.id = id;
		this.iconClass = iconClass;
		this.iconColor = iconColor;
		this.title = title;
		this.url = url;
	}
}
