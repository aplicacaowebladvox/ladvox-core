package br.com.jovencio.projetofonocore.model.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemDTO {

	private Long id;
	private String iconClass;
	private String name;
	private String description;
	private boolean isOpenned;
	private String path;
	private boolean openInNewTab;
	private List<MenuItemDTO> subMenus;
}
